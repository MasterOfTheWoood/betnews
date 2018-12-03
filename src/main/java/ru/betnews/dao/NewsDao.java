package ru.betnews.dao;

import ru.betnews.criteria.NewsCriteria;
import ru.betnews.entity.News;
import ru.betnews.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 11:38
 *
 */
public interface NewsDao  extends Dao<News, Long> {
    List<News> getByCriteria(NewsCriteria newsCriteria);

    List<News> getUserNews(User user);

    List<News> getCarousel();

    List<News> getPopularNews();
}
