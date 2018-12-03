package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;
import ru.betnews.entity.Bet;
import ru.betnews.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 09.11.13
 * Time: 17:01
 * Для регистрации пользователя
 */
@JsonSerialize
public class UserDTO {
    private final DateFormat dateFormat = new SimpleDateFormat("dd.DD.yyyy");
    private Long id;
    private String userNick = "anonymous";
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthday;
    private String email;
    private String avatarUrl;
    private MultipartFile avatar;
    private List<BetDTO> bets = new ArrayList<>();
    private AccountDTO account;

    public UserDTO(){}
    public UserDTO(User user){
        id = user.getId();
        userNick = user.getUserNick();
        email = user.getEmail();
        avatarUrl = user.getAvatar();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        if(user.getBirthday() != null)
        birthday = dateFormat.format(user.getBirthday());
        for(Bet bet: user.getBets()){
            if(!bet.getOutcome().getNews().isArchive()) {
                BetDTO betDTO = new BetDTO(bet);
                bets.add(betDTO);
            }
        }
        this.account = new AccountDTO(user.getAccount());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public Date getBirthdayDate() throws ParseException {
        return birthday != null? dateFormat.parse(birthday) : null;
    }

    public void setBirthday(Date birthday) {
        if(birthday != null)
            this.birthday = dateFormat.format(birthday);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<BetDTO> getBets() {
        return bets;
    }

    public void setBets(List<BetDTO> bets) {
        this.bets = bets;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
}
