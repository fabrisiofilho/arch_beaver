package br.com.ff.arch_beaver.modules.user.domain.service;

import br.com.ff.arch_beaver.modules.user.domain.entity.UserSessionEntity;
import br.com.ff.arch_beaver.modules.user.presentation.dto.SessionDTO;

public interface UserSessionService {

    void registerSessionWeb(SessionDTO session);
    UserSessionEntity findUserByToken(String token);

}
