package ru.betnews.entity;

import ru.betnews.entity.dto.TypeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 16:39
 * Тип новости: личная или публичная
 */
public enum NewsType {
    PUBLIC("news.type.public"),
    PRIVATE("news.type.private");

    String type;
    NewsType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static List<TypeDTO> getTypesDTOS() {
        List<TypeDTO> topics = new ArrayList<TypeDTO>();
        for(NewsType newsTopic : values()){
            TypeDTO topicDTO = new TypeDTO();
            topicDTO.setKey(newsTopic.name());
            topicDTO.setName(newsTopic.getType());
            topics.add(topicDTO);
        }
        return topics;
    }
}
