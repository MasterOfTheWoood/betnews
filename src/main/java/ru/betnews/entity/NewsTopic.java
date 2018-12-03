package ru.betnews.entity;

import ru.betnews.entity.dto.TopicDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 01.09.14
 * Time: 10:20
 * тип новости
 */
public enum NewsTopic {
    SPORT("news.topic.sport"),
    ECONOMY("news.topic.economy"),
    CULTURE("news.topic.culture"),
    SCIENCE("news.topic.science"),
    OTHER("news.topic.other");
    String topic;
    NewsTopic(String topic){
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public  static Collection<String> getTopics(){
        Collection<String> topics = new ArrayList<String>();
        for(NewsTopic newsTopic : values())
            topics.add(newsTopic.getTopic());
        return topics;
    }

    public static List<TopicDTO> getTopicDTOs() {
        List<TopicDTO> topics = new ArrayList<TopicDTO>();
        for(NewsTopic newsTopic : values()){
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setKey(newsTopic.name());
            topicDTO.setName(newsTopic.getTopic());
            topics.add(topicDTO);
        }
        return topics;
    }
}
