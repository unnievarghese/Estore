package com.example.Estore.Estore;

import com.example.Estore.Estore.io.Entity.User.AuthorityEntity;
import com.example.Estore.Estore.io.Entity.User.RoleEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.AuthorityRepository;
import com.example.Estore.Estore.io.Repositories.User.RoleRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class allows Spring to automatically detect our custom beans instantiate them and inject any specified dependencies into them.
 */
@Component
public class InitialUserSetup {

    /**
     * Inject AuthorityRepository dependency.
     */
    @Autowired
    AuthorityRepository authorityRepository;
    /**
     * Inject RoleRepository dependency.
     */
    @Autowired
    RoleRepository roleRepository;
    /**
     * Inject UserRepository dependency.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * This method runs right after springBoot starts and creates authorities,roles and if admin is missing creates an admin.
     * @param event Event
     */
    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleVisitor=createRole("ROLE_VISITOR", List.of(readAuthority));
        RoleEntity roleAdmin=createRole("ROLE_ADMIN",Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
        RoleEntity roleBuyer=createRole("ROLE_BUYER",Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
        RoleEntity roleSeller=createRole("ROLE_SELLER",Arrays.asList(readAuthority,writeAuthority,deleteAuthority));

        UserEntity adminUser = userRepository.findByEmail("admin@gmail.com");
        if (!adminUser.getRoles().isEmpty()) return;
        Collection<RoleEntity> arrayList = new ArrayList<RoleEntity>();
        arrayList.add(roleAdmin);
        adminUser.setRoles(arrayList);
        userRepository.save(adminUser);
    }

    /**
     * This method is called to create Authorities.
     * @param name Name of authority to be created.
     * @return AuthorityEntity
     */
    @Transactional
    private AuthorityEntity createAuthority(String name){

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null){
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    /**
     * This method is called to create Roles.
     * @param name Name of role to be created.
     * @param authorities List of authorities given to this role.
     * @return RoleEntity
     */
    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){

        RoleEntity role = roleRepository.findByName(name);
        if(role == null){
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
