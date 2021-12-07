package com.example.Estore.Estore.io.Entity.User;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class is used to create a table in the database.
 */
@Entity
@Table(name="roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -4645806135978947897L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> user;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="role_authorities",
            joinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id",referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity(){}

    /**
     * Class constructor.
     * @param name role name.
     */
    public RoleEntity(String name) {
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
     * Method is used to get name of role.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Method is used to set name of role.
     * @param name name of role.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method is used to get collection of userEntity.
     * @return Collection<UserEntity>
     */
    public Collection<UserEntity> getUser() {
        return user;
    }

    /**
     * Method is used to set Collection<UserEntity>.
     * @param user Collection<UserEntity>
     */
    public void setUser(Collection<UserEntity> user) {
        this.user = user;
    }

    /**
     * Method is used to get Collection<AuthorityEntity>
     * @return Collection<AuthorityEntity>
     */
    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    /**
     * Method is used to set Collection<AuthorityEntity>.
     * @param authorities Collection<AuthorityEntity>
     */
    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
