package br.com.ff.arch_beaver.common.security.controller;

import br.com.ff.arch_beaver.common.security.models.RefreshToken;
import br.com.ff.arch_beaver.common.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshToken> refreshToken(@RequestBody RefreshToken response) {
        RefreshToken request = authService.refreshToken(response);
        return ResponseEntity.ok(request);
    }

}
