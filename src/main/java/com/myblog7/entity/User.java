package com.myblog7.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//1st word money is for User,2nd word money is for Role
    @JoinTable(name = "user_roles",//joiming 2 tables Role and User using third table user_roles
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName//table 3 has  user_id column mapped toprimary keyb of parent table users
                    = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",//table 3 has role_id column mapped to primary key of  Child class tble Roles
                    referencedColumnName = "id"))
    private Set<Role> roles;
}
