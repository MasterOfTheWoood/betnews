package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.Comment;
import ru.betnews.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 08.05.2017.
 */
@JsonSerialize
public class CommentDTO {
    private Long id;
    private String description;
    private UserMinDTO user;
    private String date;
    private List<Long> likes = new ArrayList<>();
    private List<Long> dislikes = new ArrayList<>();
    private static  final String datePattern = "HH:mm dd.MM.yyyy";
    private List<CommentDTO> replays = new ArrayList<>();

    public CommentDTO(){}

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.user = new UserMinDTO(comment.getUser());
        this.date = formatDate(comment.getDate());
        for(User liker : comment.getLikes())
            this.likes.add(liker.getId());
        for(User disliker : comment.getDislikes())
            this.dislikes.add(disliker.getId());
        for(Comment replay : comment.getReplays())
            this.replays.add(new CommentDTO(replay));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserMinDTO getUser() {
        return user;
    }

    public void setUser(UserMinDTO user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = formatDate(date);
    }

    public List<Long> getLikes() {
        return likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }

    public List<Long> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Long> dislikes) {
        this.dislikes = dislikes;
    }

    public List<CommentDTO> getReplays() {
        return replays;
    }

    public void setReplays(List<CommentDTO> replays) {
        this.replays = replays;
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
