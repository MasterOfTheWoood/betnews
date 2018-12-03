package ru.betnews.dao;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 14.08.12
 * Time: 14:35
 * Оснойной дао
 */
public interface Dao<T, PK extends Serializable> {
    T create(T newInstance);

    T read(PK id);

    T readAndLock(PK id);

    void update(T transientObject);

    void delete(T persistentObject);

    void saveOrUpdate(T instance);

    boolean refreshAndLockNoWait(T instance);

    void refreshAndLock(T instance);

    boolean lock(T instance);

    boolean lockNoWait(T instance);

    boolean refresh(T instance);
}

