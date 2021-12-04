package com.example.Estore.Estore.Security;

import com.example.Estore.Estore.io.Entity.User.AuthorityEntity;
import com.example.Estore.Estore.io.Entity.User.RoleEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class is used to return the user's roles and authorities.
 */
public class UserPrincipal  {

    UserEntity userEntity;

    /**
     * Class constructor.
     * @param userEntity UserEntity.
     */
    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Method used to get all the roles and authorities the user has.
     * @return Collection<? extends GrantedAuthority>
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntity = new HashSet<>();

        Collection<RoleEntity> roles = userEntity.getRoles();

        if (roles == null) return authorities;

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntity.addAll(role.getAuthorities());
        });

        authorityEntity.forEach((authorityEntityobj) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntityobj.getName()));
        });

        return authorities;
    }
}
