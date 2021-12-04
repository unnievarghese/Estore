package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class is used to create a table in the database.
 */
@Entity
@Table(name="authority")
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = -4937737079349354897L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;

    public AuthorityEntity(){}

    /**
     * Class constructor.
     * @param name name of authority.
     */
    public AuthorityEntity(String name) {
        this.name = name;
    }

    /**
     * Method is used to get database id.
     * @return long
     */
    public Long getId() {
        return id;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method is used to get name of authority.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Method is used to set name of authority.
     * @param name name of authority.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method is used to get Collection<RoleEntity>.
     * @return Collection<RoleEntity>
     */
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    /**
     * Method is used to set Collection<RoleEntity>
     * @param roles Collection<RoleEntity>
     */
    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

}
