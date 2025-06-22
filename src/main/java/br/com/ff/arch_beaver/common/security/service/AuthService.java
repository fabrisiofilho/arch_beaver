package br.com.ff.arch_beaver.common.security.service;

import br.com.ff.arch_beaver.common.security.models.RefreshToken;

public interface AuthService {

    RefreshToken refreshToken(RefreshToken response);

}
