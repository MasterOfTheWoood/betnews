package ru.betnews.web.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.betnews.criteria.NewsCriteria;
import ru.betnews.entity.News;
import ru.betnews.entity.NewsTopic;
import ru.betnews.entity.NewsType;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.CommentDTO;
import ru.betnews.entity.dto.NewsDTO;
import ru.betnews.entity.dto.OutcomeDTO;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.news.NewsService;
import ru.betnews.service.security.SecurityContextHelper;
import ru.betnews.service.user.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 20.03.15
 * Time: 23:15
 *
 */
@Controller
@SessionAttributes("newsCriteria")
@RequestMapping("/member/news")
public class NewsController {

    private final SecurityContextHelper securityContextHelper;
    private final MemoryUserService memoryUserService;
    private final NewsService newsService;
    private final UserService userService;

    @Autowired
    public NewsController(SecurityContextHelper securityContextHelper, MemoryUserService memoryUserService,
                          NewsService newsService, UserService userService) {
        this.securityContextHelper = securityContextHelper;
        this.memoryUserService = memoryUserService;
        this.newsService = newsService;
        this.userService = userService;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public NewsDTO getNews(@PathVariable("id") long newsId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        if(user !=  null) {
            News news = newsService.getById(newsId);
            return new NewsDTO(news);
        }
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public NewsDTO createNews(@RequestBody NewsDTO newsDTO){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        if(user != null)
            newsDTO = newsService.create(newsDTO, user);
        return newsDTO;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public NewsDTO editNews(@RequestBody NewsDTO newsDTO){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        if(user != null)
            try {
                newsService.edit(newsDTO);
            } catch (Exception e) {

            }
        return newsDTO;
    }


    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public CommentDTO addComment(@RequestParam("newsId") long newsId, @RequestParam("comment") String comment,
                                 @RequestParam(value = "replayComment", required = false) Long replayComment){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        return newsService.addComment(newsId, comment, user, replayComment);
    }


    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> vote(@RequestParam("newsId") long newsId, @RequestParam("outcomeId") long outcomeId,
                           @RequestParam("value") double value){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        OutcomeDTO outcomeDTO;
        try {
            outcomeDTO =  newsService.outcomeVote(newsId, outcomeId, value, user);
            return new ResponseEntity<>(outcomeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Одобрить ставки на новость.
     * Нужно для того, что бы все участники закрытой новости были согласны с условиями
     * и со ставками друг друга. Каждый участник должен подтвердить, что он согласен со всем.
     * Как только хотя бы один участник одобрил новость, менять ставки и голосовать за иной исход
     * не возможно. Как только все участики согласовали новость, останется только ждать исхода новости.
     * @param newsId - новость
     * @return - успешно или нет
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> approve(@RequestParam("newsId") long newsId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        newsService.approve(newsId, user);
        return ResponseEntity.ok("");
    }

    /**
     * Подтверждение исхода. Подтвердить исход может только куратор новости
     * @param outcomeId - исход
     * @return - успешно или нет
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> confirm(@RequestParam("outcomeId") long outcomeId,
                                     @RequestParam(value = "confirmation", required = false) String confirmation,
                                     @RequestParam(value = "comment", required = false) String comment){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        newsService.confirm(outcomeId, user, confirmation, comment);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<NewsDTO>> getMyNews(){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        List<NewsDTO> newsDTOList = new ArrayList<>();
        if(user != null)
            newsDTOList = newsService.getUserNews(user);
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/like/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<NewsDTO> likeNews(@RequestParam("newsId") long newsId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        NewsDTO newsDTO = newsService.likeNews(newsId, user);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<NewsDTO> dislikeNews(@RequestParam("newsId") long newsId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        NewsDTO newsDTO =newsService.dislikeNews(newsId, user);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/comment/like/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommentDTO> likeComment(@RequestParam("commentId") long commentId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        CommentDTO commentDTO = newsService.likeComment(commentId, user);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/comment/dislike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommentDTO> dislikeComment(@RequestParam("commentId") long commentId){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        CommentDTO commentDTO =newsService.dislikeComment(commentId, user);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

}
