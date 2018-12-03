package ru.betnews.web.security;


/**
 * Created by dell on 14.04.2018.
 */
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(){}

    public AuthenticationResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
