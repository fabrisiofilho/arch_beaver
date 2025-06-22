package br.com.ff.arch_beaver.common.security.details;

import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetails(UserEntity user, List<String> roles) {
        super();
        this.id = user.getId();
        this.email = user.getEmail().getEmail();
        this.password = user.getPassword();
        this.enabled = user.getActive();
        this.authorities = getAuthoritiesUser(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    private Collection<GrantedAuthority> getAuthoritiesUser(List<String> roles){
        return new HashSet<>(roles.stream().map(SimpleGrantedAuthority::new).toList());
    }

}