package br.com.ff.arch_beaver.modules.user.domain.service.impl;

import br.com.ff.arch_beaver.common.error.exception.auth.TokenException;
import br.com.ff.arch_beaver.common.error.exception.general.NotFoundException;
import br.com.ff.arch_beaver.common.security.utils.TokenJWTSecurity;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserSessionEntity;
import br.com.ff.arch_beaver.modules.user.presentation.dto.SessionDTO;
import br.com.ff.arch_beaver.modules.user.domain.repository.UserRepository;
import br.com.ff.arch_beaver.modules.user.domain.repository.UserSessionRepository;
import br.com.ff.arch_beaver.modules.user.domain.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final TokenJWTSecurity tokenJWTSecurity;
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    @Override
    public void registerSessionWeb(SessionDTO session) {
        var user = userRepository.findById(session.getUserId()).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        InetAddress address;

        try {
            address = InetAddress.getLocalHost();
        } catch(UnknownHostException unknownHostException) {
            throw new NotFoundException("IP do host");
        }

        userSessionRepository.save(
            UserSessionEntity.builder()
                .user(user)
                .start(LocalDateTime.now())
                .term((tokenJWTSecurity.getExpiration(session.getToken())))
                .token(session.getToken())
                .address(address)
                .lastAccess(LocalDate.now())
                .build()
        );
    }

    @Override
    public UserSessionEntity findUserByToken(String token) {
        return userSessionRepository.findByToken(token, LocalDateTime.now()).orElseThrow(() -> new TokenException("Token não encontrado."));
    }

}
