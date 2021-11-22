package com.example.Estore.Estore.io.Entity.User;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

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

    public RoleEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUser() {
        return user;
    }

    public void setUser(Collection<UserEntity> user) {
        this.user = user;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
