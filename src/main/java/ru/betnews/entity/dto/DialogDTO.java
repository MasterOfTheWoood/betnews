package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by dell on 23.06.2018.
 */
@JsonSerialize
public class DialogDTO {
    private UserMinDTO user;
    private MessageDTO mail;

    public DialogDTO(UserMinDTO userMinDTO, MessageDTO messageDTO) {
        this.user = userMinDTO;
        this.mail = messageDTO;
    }

    public UserMinDTO getUser() {
        return user;
    }

    public void setUser(UserMinDTO user) {
        this.user = user;
    }

    public MessageDTO getMail() {
        return mail;
    }

    public void setMail(MessageDTO mail) {
        this.mail = mail;
    }
}
