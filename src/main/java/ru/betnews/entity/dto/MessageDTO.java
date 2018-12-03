package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
@JsonSerialize
public class MessageDTO {
    private long id;
    private UserMinDTO userTo;
    private UserMinDTO userFrom;
    private String message;
    private String date;
    private static  final String datePattern = "HH:mm dd.MM.yyyy";

    public MessageDTO(){}
    public MessageDTO(Message message){
        this.id = message.getId();
        this.message = message.getMessage();
        this.userFrom = new UserMinDTO(message.getUserFrom());
        this.userTo = new UserMinDTO(message.getUserTo());
        this.date =  formatDate(message.getDate());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserMinDTO getUserTo() {
        return userTo;
    }

    public void setUserTo(UserMinDTO userTo) {
        this.userTo = userTo;
    }

    public UserMinDTO getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(UserMinDTO userFrom) {
        this.userFrom = userFrom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = formatDate(date);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String formatDate(Date date){
        if(new Date().getTime() - date.getTime() < 60*1000)
            return "менее минуты назад";
        if(new Date().getTime() - date.getTime() < 3600*1000) {
            int min = ((int) (new Date().getTime() - date.getTime()) / 1000 * 60);
            if(min == 1 || (min > 20 && min %10 == 1))
                return min + "минуту назад";
            else if(min > 1 && min < 5 || (min > 20 && min %10 > 1 && min % 10 < 5))
                return min + "минуты назад";
            else
                return min + "минут назад";
        }
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        return format.format(date);
    }
}
