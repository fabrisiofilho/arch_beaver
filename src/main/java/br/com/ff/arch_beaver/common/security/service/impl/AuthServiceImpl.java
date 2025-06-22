package br.com.ff.arch_beaver.common.security.service.impl;

import br.com.ff.arch_beaver.common.error.exception.auth.TokenException;
import br.com.ff.arch_beaver.common.security.models.RefreshToken;
import br.com.ff.arch_beaver.common.security.service.AuthService;
import br.com.ff.arch_beaver.common.security.utils.TokenJWTSecurity;
import br.com.ff.arch_beaver.modules.user.presentation.dto.SessionDTO;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import br.com.ff.arch_beaver.modules.user.domain.repository.UserRepository;
import br.com.ff.arch_beaver.modules.user.domain.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenJWTSecurity tokenJWTSecurity;
    private final UserSessionService userSessionService;

    @Override
    @Transactional
    public RefreshToken refreshToken(RefreshToken response) {
        if (!tokenJWTSecurity.tokenValid(response.getRefreshToken())) {
            throw new TokenException("Token invalido.");
        }

        String email = tokenJWTSecurity.getUserEmail(response.getRefreshToken());
        UserEntity user = userRepository.findByLogin(email);

        String token = tokenJWTSecurity.generateTokenWeb(email);
        String refreshToken = tokenJWTSecurity.generateRefreshToken(email);

        userSessionService.registerSessionWeb(SessionDTO.builder()
            .userId(user.getId())
            .token(token)
            .build());

        return RefreshToken.builder()
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }

}
