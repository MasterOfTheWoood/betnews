package ru.betnews.service.news;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.betnews.criteria.NewsCriteria;
import ru.betnews.dao.BetDao;
import ru.betnews.dao.CommentDao;
import ru.betnews.dao.NewsDao;
import ru.betnews.dao.OutcomeDao;
import ru.betnews.entity.*;
import ru.betnews.entity.dto.*;
import ru.betnews.service.user.RemittanceService;
import ru.betnews.service.user.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 20.03.15
 * Time: 22:16
 * Сервис новостей
 */
@Service
public class NewsServiceImpl implements  NewsService{

    @Resource
    private NewsDao newsDao;
    @Resource
    private OutcomeDao outcomeDao;
    @Resource
    private CommentDao commentDao;
    @Resource
    private BetDao betDao;
    @Resource
    private RemittanceService remittanceService;

    @Override
    @Transactional(readOnly = true)
    public List<News> list(NewsCriteria newsCriteria) {
        return newsDao.getByCriteria(newsCriteria);
    }

    @Override
    @Transactional(readOnly = true)
    public News getById(long id) {
        return newsDao.read(id);
    }

    @Override
    @Transactional
    public NewsDTO create(NewsDTO newsDTO, User user) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setDescription(newsDTO.getDescription());
        news.setNewsTopic(newsDTO.getNewsTopic());
        news.setNewsType(newsDTO.getNewsType());
        news.setUser(user);
        if(newsDTO.getNewsType() == NewsType.PUBLIC)
            news.setArbiter(user);
        news = newsDao.create(news);
        for(OutcomeDTO outcomeDTO: newsDTO.getOutcomes()){
            Outcome outcome =  new Outcome();
            outcome.setDescription(outcomeDTO.getDescription());
            outcome.setNumber(outcomeDTO.getNumber());
            outcome.setNews(news);
            outcome = outcomeDao.create(outcome);
            news.getOutcomes().add(outcome);
        }
        return new NewsDTO(news);
    }

    @Override
    @Transactional
    public void delete(long newsId) {
        News news = getById(newsId);
        news.setArchive(true);
        news.setArchiveDate(new Date());
        newsDao.saveOrUpdate(news);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDTO> getNewsList(NewsCriteria newsCriteria) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for(News news : newsDao.getByCriteria(newsCriteria)){
            NewsDTO newsDTO = new NewsDTO(news);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }

    @Override
    @Transactional
    public CommentDTO addComment(long newsId, String description, User user, Long replayComment) {
        News news = newsDao.read(newsId);
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setDescription(description);
        comment.setNews(news);
        comment.setUser(user);
        if(replayComment != null){
            Comment replay = commentDao.read(replayComment);
            if(replay != null){
                comment.setReplay(replay);
            }
        }
        comment = commentDao.create(comment);
        return new CommentDTO(comment);
    }

    @Override
    @Transactional
    public OutcomeDTO outcomeVote(long newsId, long outcomeId, double value, User user) throws Exception {
        Outcome outcome = outcomeDao.read(outcomeId);
        News news = newsDao.read(newsId);
        if(news.getArbiter().equals(user)) throw new Exception("can.not.vote.arbiter");//Арбитр не может голосовать за новость!
        if(news.isArchive()) throw new Exception("can.not.vote.archive");//Нельзя голосовать за архивную новость!
        if(news.getNewsType().equals(NewsType.PRIVATE) && !news.getCanVoteUsers().contains(user)) throw new Exception("can.not.vote.private");//За эту новость Вам нельзя голосовать!
        if(user.getAccount().getBalance() < value) throw new Exception("can.not.vote.money");//Вам не хватает срадств, что бы сделать такую ставку!
        for(Outcome o : news.getOutcomes()){
            for(Bet bet : o.getBets())
                if(bet.getUser().equals(user))
                    throw new Exception("can.not.vote.already");//Вы уже голосовали за эту новость!
        }
        remittanceService.payToSystem(user, value);
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setOutcome(outcome);
        bet.setBetValue(value);
        bet = betDao.create(bet);
        outcome.getBets().add(bet);
        return new OutcomeDTO(outcome);
    }

    @Override
    public NewsDTO edit(NewsDTO newsDTO) throws Exception {
        News news = newsDao.read(newsDTO.getId());
        if(news != null){
            news.setTitle(newsDTO.getTitle());
            news.setDescription(newsDTO.getDescription());
            news.setNewsTopic(newsDTO.getNewsTopic());
            news.setNewsType(newsDTO.getNewsType());
            //TODO edit outcomes, проверка, что новость не отркрыта
            newsDao.saveOrUpdate(news);
        } else throw new Exception("news.not.found");
        return new NewsDTO(news);
    }

    @Override
    public void approve(long newsId, User user) {
        News news = newsDao.read(newsId);
        if(news != null){

        }
    }

    @Override
    @Transactional
    public void confirm(long outcomeId, User user, String confirmation, String commentDesc) {
        Outcome outcome = outcomeDao.read(outcomeId);
        if(outcome != null){
            outcome.setConfirmed(true);
            outcomeDao.saveOrUpdate(outcome);
            //Добавить комментарий
            String description = "Арбитр " + user.getUserNick() + "подтвердил исход новости \"" + outcome.getDescription() + "\".";
            description = confirmation != null? description + "<br/>Cсылка на подтверждение: " + confirmation : description;
            description = commentDesc != null? description + "<br/>Комментарий: " + commentDesc : description;
            addComment(outcome.getNews().getId(), description, user, null);
            //Рассчитать награду
            //Рассчитать рейтинг
            outcome.getNews().setArchive(true);
            newsDao.saveOrUpdate(outcome.getNews());
        }
    }

    @Override
    public List<NewsDTO> getUserNews(User user) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for(News news : newsDao.getUserNews(user)){
            NewsDTO newsDTO = new NewsDTO(news);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }

    @Override
    @Transactional
    public CommentDTO likeComment(long commentId, User user) {
        Comment comment = commentDao.read(commentId);
        if(!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
            if(comment.getDislikes().contains(user))
                comment.getDislikes().remove(user);
            commentDao.saveOrUpdate(comment);
        }
        return new CommentDTO(comment);
    }

    @Override
    @Transactional
    public CommentDTO dislikeComment(long commentId, User user) {
        Comment comment = commentDao.read(commentId);
        if(!comment.getDislikes().contains(user)) {
            comment.getDislikes().add(user);
            if(comment.getLikes().contains(user))
                comment.getLikes().remove(user);
            commentDao.saveOrUpdate(comment);
        }
        return new CommentDTO(comment);
    }

    @Override
    @Transactional
    public NewsDTO likeNews(long newsId, User user) {
        News news = newsDao.read(newsId);
        if(!news.getLikes().contains(user)) {
            news.getLikes().add(user);
            if(news.getDislikes().contains(user))
                news.getDislikes().remove(user);
            newsDao.saveOrUpdate(news);
        }
        return new NewsDTO(news);
    }

    @Override
    @Transactional
    public NewsDTO dislikeNews(long newsId, User user) {
        News news = newsDao.read(newsId);
        if(!news.getDislikes().contains(user)) {
            news.getDislikes().add(user);
            if(news.getLikes().contains(user))
                news.getLikes().remove(user);
            newsDao.saveOrUpdate(news);
        }
        return new NewsDTO(news);
    }

    public List<NewsDTO> getCarousel(){
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for(News news : newsDao.getCarousel()){
            newsDTOList.add(new NewsDTO(news));
        }
        return newsDTOList;
    }

    @Override
    public List<NewsDTO> getPopularNews() {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for(News news : newsDao.getPopularNews()){
            newsDTOList.add(new NewsDTO(news));
        }
        return newsDTOList;
    }
}
