package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;
import ru.betnews.entity.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 21.03.15
 * Time: 13:47
 *
 */
@JsonSerialize
public class NewsDTO {
    private Long id;
    private String title;
    private MultipartFile image;
    private String illustration;
    private String description;
    private NewsTopic newsTopic;
    private NewsType newsType;
    private int spam;
    //Дата создания
    private Date createDate;
    private UserMinDTO author;
    private UserMinDTO arbiter;
    private List<CommentDTO> comments = new ArrayList<CommentDTO>();
    private List<OutcomeDTO> outcomes = new ArrayList<OutcomeDTO>();
    private Set<UserMinDTO> canVoteUsers = new HashSet<>();
    private List<Long> likes = new ArrayList<>();
    private List<Long> dislikes = new ArrayList<>();

    public NewsDTO(){}
    public NewsDTO(News news){
        this.id = news.getId();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.newsTopic = news.getNewsTopic();
        this.newsType = news.getNewsType();
        this.spam = news.getSpam();
        this.createDate = news.getCreateDate();
        this.author = new UserMinDTO(news.getUser());
        if(news.getArbiter() != null)
            this.arbiter = new UserMinDTO(news.getArbiter());
        for(Comment comment : news.getComments()) {
            if(comment.getReplay() == null) {
                CommentDTO commentDTO = new CommentDTO(comment);
                this.comments.add(commentDTO);
            }
        }

        for(Outcome outcome : news.getOutcomes()){
            OutcomeDTO outcomeDTO = new OutcomeDTO(outcome);
            this.outcomes.add(outcomeDTO);
        }

        for (User user : news.getCanVoteUsers()){
            UserMinDTO userMinDTO = new UserMinDTO(user);
            canVoteUsers.add(userMinDTO);
        }

        for(User liker : news.getLikes())
            this.likes.add(liker.getId());
        for(User disliker : news.getDislikes())
            this.dislikes.add(disliker.getId());
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public NewsTopic getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public int getSpam() {
        return spam;
    }

    public void setSpam(int spam) {
        this.spam = spam;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserMinDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserMinDTO author) {
        this.author = author;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<OutcomeDTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }

    public UserMinDTO getArbiter() {
        return arbiter;
    }

    public void setArbiter(UserMinDTO arbiter) {
        this.arbiter = arbiter;
    }

    public Set<UserMinDTO> getCanVoteUsers() {
        return canVoteUsers;
    }

    public void setCanVoteUsers(Set<UserMinDTO> canVoteUsers) {
        this.canVoteUsers = canVoteUsers;
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

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }
}
