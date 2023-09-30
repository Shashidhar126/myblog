package com.myblog7.config;



import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import com.myblog7.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {//security related,this class help me to retreive the data from database
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {//automatically spring security takes username from login dto object
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)//based on username it will go to database and will search the record based on username or email,if username exists user object is intialized,if not it will throw exception
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return new CustomUserDetails(user);//conterol moves to customeruserdetails
    }

}
