package ru.betnews.dao;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 14.08.12
 * Time: 14:36
 * Основной Абстрактный класс
 */

@Repository("dao")
public abstract class AbstractHibernateDao<T, PK extends Serializable> {
    private final Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;


    public AbstractHibernateDao(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings({"unchecked"})
    public T create(T newInstance) {
        return entityManager.merge(newInstance);
    }

    public T read(PK id) {
        return (T) entityManager.find(type, id);
    }

    public T readAndLock(PK id)
    {
        return (T) entityManager.find(type, id, LockModeType.PESSIMISTIC_WRITE);
    }

    public void update(T transientObject) {
        entityManager.merge(transientObject);
    }

    public void delete(T persistentObject) {
        entityManager.detach(persistentObject);
    }

    public void saveOrUpdate(T instance) {
        entityManager.persist(instance);
    }

    public boolean lock(T instance)
    {
        try
        {
            entityManager.lock(instance, LockModeType.PESSIMISTIC_WRITE);

            return true;
        }
        catch (LockAcquisitionException e)
        {
            return false;
        }
    }

    public boolean lockNoWait(T instance)
    {
        try
        {
            entityManager.lock(instance, LockModeType.NONE);

            return true;
        }
        catch (LockAcquisitionException e)
        {
            return false;
        }
    }

    public boolean refresh(T instance)
    {
        try
        {
            entityManager.refresh(instance);

            return true;
        }
        catch (LockAcquisitionException e)
        {
            return false;
        }
    }

    public boolean refreshAndLockNoWait(T instance)
    {
        try
        {
            entityManager.refresh(instance, LockModeType.NONE);

            return true;
        }
        catch (LockAcquisitionException e)
        {
            return false;
        }
    }

    public void refreshAndLock(T instance)
    {
        entityManager.refresh(instance, LockModeType.PESSIMISTIC_WRITE);
    }
}
