package br.com.ff.arch_beaver.common.security.details;

import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import br.com.ff.arch_beaver.modules.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    /*
     * Classe responsável por carregar os detalhes do usuário autenticado e suas permissões.
     * Responsável pela configuração dos dados da visualização e da entidade associada ao usuário logado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByLogin(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail informado");
        }
        return new AuthUserDetails(user, List.of("ADMIN"));
    }

}
