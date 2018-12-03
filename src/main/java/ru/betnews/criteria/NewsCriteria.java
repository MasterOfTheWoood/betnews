package ru.betnews.criteria;

import ru.betnews.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 20.03.15
 * Time: 22:18
 * Поиск новостей
 */
public class NewsCriteria {
    private String category;
    private User user;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
