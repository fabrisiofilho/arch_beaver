package br.com.ff.arch_beaver.modules.user.domain.service;

import br.com.ff.arch_beaver.common.security.models.UserLogin;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;

public interface UserService {

    UserEntity findByLogin(String login);
    UserLogin login(String email);
    void updateToken(String token, String email);
    UserEntity findByToken(String token);

}
