package br.com.brunno.mercadoLivre.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewUserResponse {

    private final String login;
    private final LocalDateTime createDate;

    public NewUserResponse(User user) {
        this.login = user.getLogin();
        this.createDate = user.getCreateDate();
    }
}
