package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.CommentDao;
import ru.betnews.entity.Comment;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 12:02
 *
 */

@Repository
public class CommentDaoImpl extends AbstractHibernateDao<Comment, Long> implements CommentDao{
    public CommentDaoImpl(){
        super(Comment.class);
    }
}
