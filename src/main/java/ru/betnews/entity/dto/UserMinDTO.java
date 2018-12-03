package ru.betnews.entity.dto;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.User;

/**
 * Created by Evgeniy.Guzeev on 03.04.2018.
 */
@JsonSerialize
public class UserMinDTO {
    private Long id;
    private String userNick = "anonymous";
    private String avatarUrl;

    public UserMinDTO(){}

    public UserMinDTO(User user){
        this.id = user.getId();
        this.userNick = user.getUserNick();
        this.avatarUrl = user.getAvatar();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
