package br.com.ff.arch_beaver.modules.user.domain.service.impl;

import br.com.ff.arch_beaver.common.config.UserContext;
import br.com.ff.arch_beaver.common.error.exception.auth.AuthenticationSeifException;
import br.com.ff.arch_beaver.common.security.models.UserLogin;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserSessionEntity;
import br.com.ff.arch_beaver.modules.user.domain.repository.UserRepository;
import br.com.ff.arch_beaver.modules.user.domain.service.UserService;
import br.com.ff.arch_beaver.modules.user.domain.service.UserSessionService;
import br.com.ff.arch_beaver.modules.user.presentation.dto.SessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSessionService userSessionService;

    @Override
    public UserEntity findByLogin(String login) {
        UserEntity user = userRepository.findByLogin(login);

        if (Objects.nonNull(user) && user.getActive().equals(Boolean.FALSE)) {
            throw new AuthenticationSeifException("Usuário está inativo");
        }

        if (Objects.isNull(user)) {
            throw new AuthenticationSeifException("Usuário informado não está cadastrado");
        }

        return user;
    }

    @Override
    public UserLogin login(String email) {
        UserEntity user = userRepository.findByLogin(email);
        return new UserLogin(
            user.getId(),
            user.getEmail().getEmail(),
            user.getProfileDescription(),
            user.getProfileTypeKey()
        );
    }

    @Override
    public void updateToken(String token, String email) {
        UserEntity object = findByLogin(email);
        UserContext.setCurrentUser(object);
        userSessionService.registerSessionWeb(
            SessionDTO.builder()
                .userId(object.getId())
                .token(token)
                .build()
        );
        userRepository.save(object);
        UserContext.clearUser();
    }

    @Override
    public UserEntity findByToken(String token) {
        UserSessionEntity userSession = userSessionService.findUserByToken(token);
        UserContext.setCurrentSession(userSession);
        return userSession.getUser();
    }

}
