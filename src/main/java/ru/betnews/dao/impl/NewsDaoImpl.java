package ru.betnews.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.betnews.criteria.NewsCriteria;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.NewsDao;
import ru.betnews.entity.News;
import ru.betnews.entity.NewsTopic;
import ru.betnews.entity.User;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 11:43
 *
 */
@Repository
public class NewsDaoImpl extends AbstractHibernateDao<News, Long> implements NewsDao {

    public NewsDaoImpl(){
       super(News.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<News> getByCriteria(NewsCriteria newsCriteria) {
        /*Criteria criteria = entityManager.getCriteriaBuilder();//.News.class);
        if(newsCriteria != null) {
            if (newsCriteria.getCategory() != null)
                criteria.add(Restrictions.eq("newsTopic", NewsTopic.valueOf(newsCriteria.getCategory().toUpperCase())));
        }
        return criteria.list();*/

        CriteriaQuery<News> criteria = entityManager.getCriteriaBuilder().createQuery( News.class );
        Root<News> newsRoot = criteria.from( News.class );
        criteria.select( newsRoot );

        /*if(newsCriteria != null) {
            if (newsCriteria.getCategory() != null)
                criteria.where(Restrictions.eq("newsTopic", NewsTopic.valueOf(newsCriteria.getCategory().toUpperCase())));

            Predicate predicate = new
        }*/
        return entityManager.createQuery( criteria ).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<News> getUserNews(User user){
        return entityManager.createQuery("from News where user.id = :userId or arbiter.id = :userId or " +
                "id in (select n.id from Bet b left join Outcome o on b.outcome.id = o.id left join News n " +
                "on n.id = o.news.id where b.user.id = :userId)").setParameter("userId", user.getId()).getResultList();
    }

    public List<News> getCarousel(){
        return entityManager.createQuery("from News").setMaxResults(3).getResultList();
    }

    @Override
    public List<News> getPopularNews() {
        return entityManager.createQuery("SELECT n FROM News n RIGHT JOIN Comment c ON n.id = c.news.id ORDER BY c.date DESC").setMaxResults(3).getResultList();
    }
}
