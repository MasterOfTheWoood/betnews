package ru.betnews.service.user;

import ru.betnews.entity.User;
import ru.betnews.entity.dto.UserDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 12:10
 */
public interface UserService {
    /**
     * Регистрация пользователя
     * @param userDTO - данные с формы
     * @return готовый пользователь
     */
    User registration(UserDTO userDTO) throws Exception;

    User getUserById(long userId);

    void editUser(UserDTO user);

    boolean remindPassword(String email) throws Exception;

    void sendRemindPasswordMail(String email)throws Exception;

    List<UserDTO> findLikeName(String userName);

    boolean verifyEmail(String code);

    void sendVerifyEmailMail(User user);
}
