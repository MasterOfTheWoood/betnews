package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by Evgeniy.Guzeev on 29.03.2018.
 */
@JsonSerialize
public class TypeDTO {
    private String key;
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
