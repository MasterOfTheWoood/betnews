package ru.betnews.service.news;

import ru.betnews.criteria.NewsCriteria;
import ru.betnews.entity.News;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.CommentDTO;
import ru.betnews.entity.dto.NewsDTO;
import ru.betnews.entity.dto.OutcomeDTO;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 20.03.15
 * Time: 22:16
 */
public interface NewsService {
    List<News> list(NewsCriteria newsCriteria);

    News getById(long id);

    NewsDTO create(NewsDTO newsDTO, User user);

    void delete(long newsId);

    List<NewsDTO> getNewsList(NewsCriteria newsCriteria);

    CommentDTO addComment(long newsId, String comment, User user, Long replayComment);

    OutcomeDTO outcomeVote(long newsId, long outcomeId,  double value, User user) throws Exception;

    NewsDTO edit(NewsDTO newsDTO) throws Exception;

    void approve(long newsId, User user);

    void confirm(long outcomeId, User user, String confirmation, String comment);

    List<NewsDTO> getUserNews(User user);

    CommentDTO likeComment(long commentId, User user);

    CommentDTO dislikeComment(long commentId, User user);

    NewsDTO likeNews(long newsId, User user);

    NewsDTO dislikeNews(long newsId, User user);

    List<NewsDTO> getCarousel();

    List<NewsDTO> getPopularNews();
}
