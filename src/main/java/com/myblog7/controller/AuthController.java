package com.myblog7.controller;

import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import com.myblog7.payload.LoginDto;
import com.myblog7.payload.SignUpDto;
import com.myblog7.repository.RoleRepository;
import com.myblog7.repository.UserRepository;
import com.myblog7.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
private PasswordEncoder passwordEncoder;
    @Autowired
private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;//AuthenticationManager in Spring Boot is like a gatekeeper for your website or application. Its job is to make sure that people trying to access your site are who they say they are.

    //Imagine you have a secret club, and you only want members to enter. The AuthenticationManager checks their membership cards (usernames and passwords) to see if they're allowed in. If their card is valid, they can come in; otherwise, they're denied access.

            //So, the AuthenticationManager is like the bouncer at a club, ensuring only authorized users get in and protecting your website or app from unauthorized access. It's an essential part of keeping your online resources safe and secure.
            @Autowired
            private JwtTokenProvider tokenProvider;
    @PostMapping("/signin")//http://localhost:8080/api/auth/signin
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                           loginDto){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(//authenticationManager.authenticate(...): This line is like asking the bouncer (authenticationManager) to check if someone (the user) has the right membership card (username and password). The user submits their username and password (loginDto.getUsernameOrEmail() and loginDto.getPassword()).
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));//this steps checks in database if id exists or not by using CustomUserDetaisService class
        SecurityContextHolder.getContext().setAuthentication(authentication);//SecurityContextHolder.getContext().setAuthentication(authentication): If the bouncer (authenticationManager) confirms that the user's membership card is valid (authentication), we give the user a stamp on their hand (setAuthentication). This stamp says they're allowed to access certain parts of the club (your website or app).
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
       // return new ResponseEntity<>("User signed-in successfully!.",HttpStatus.OK);//return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK): If everything goes well, we tell the user, "You're in! Welcome to the club!" This line is like giving the user a friendly message (User signed-in successfully!) and saying they can enter the club (HTTP status OK, which means everything is good).
    }

    @PostMapping("/signup")//http://localhost:8080/api/auth/signup
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        Boolean emailExist = userRepo.existsByEmail(signUpDto.getEmail());//it will check and return boolean value if email exists
        if(emailExist){

            return new ResponseEntity<>("Email Id Exist",HttpStatus.BAD_REQUEST);
        }
        Boolean emailUserName = userRepo.existsByUsername(signUpDto.getUsername());
        if(emailUserName){
            return new ResponseEntity<>("Username Exist",HttpStatus.BAD_REQUEST);
        }
        User user=new User() ;
        user.setName(signUpDto.getName());//copy the data from signup dto to entity
       user.setEmail(signUpDto.getEmail());
       user.setUsername(signUpDto.getUsername());
       user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));//this will encrypt password


     userRepo.save(user);
        return new ResponseEntity<>("User is registered", HttpStatus.CREATED);
    }
}
