package ru.betnews.entity.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy.Guzeev on 02.04.2018.
 */
public class ResultDTO {
    private List<Res> results = new ArrayList<>();
    public ResultDTO(){}
    public ResultDTO(List<UserDTO> elements){
        for(UserDTO userDTO : elements){
            Res res = new Res();
            res.setId(userDTO.getId());
            res.setText(userDTO.getUserNick());
            results.add(res);
        }
    }

    public List<Res> getResults() {
        return results;
    }

    public void setResults(List<Res> results) {
        this.results = results;
    }

    public static class Res{
        private long id;
        private String text;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
