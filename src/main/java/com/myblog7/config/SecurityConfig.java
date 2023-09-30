package com.myblog7.config;

import com.myblog7.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration//whenever u use configeration class u have to use@Configeration,if you dont use then springboot framework will not scan this class and read this class,it will become ordibary java class and will use default config class
    @EnableWebSecurity//The @EnableWebSecurity annotation in Spring Security is used to enable and configure security for a web application. When you annotate a configuration class with @EnableWebSecurity, it tells Spring to consider that class as a configuration source for security settings and to apply security measures to your web application
    @EnableGlobalMethodSecurity(prePostEnabled = true)//The @EnableGlobalMethodSecurity(prePostEnabled = true) annotation in Spring Security is used to enable method-level security in your application. When you annotate a configuration class with this annotation, it allows you to use annotations like @PreAuthorize and @PostAuthorize to apply fine-grained security checks to individual methods within your Spring beans.//overall it controlls which method can be accessed by who??
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;//this is an object of customservicedetailsclass
    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()//security chaining filter
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic();//chaining statements in spring security
    }//csrf-In Spring Security, CSRF (Cross-Site Request Forgery) protection is essential for preventing malicious attacks that can occur when a user is tricked into making unwanted actions on a web application without their consent. Spring Security provides built-in support for CSRF protection through the csrf() configuration method
    // WHEN YOU LOGIN A SEE=SSION GETS cretaed and that gets stored in sessionid,session id is stored inside brower,session contains some information,it is like id card
//authorizererequest(),any request,authenticated-any incoming request should be authenticated in basic


//@Configuration and @EnableWebSecurity are annotations that tell Spring that this class is responsible for configuring security settings for your web application.
//
//SecurityConfig extends WebSecurityConfigurerAdapter, which is a base class provided by Spring Security that makes it easier to configure security.
//
//In the configure(HttpSecurity http) method, you are defining security rules. Here's a simple explanation of what's happening in that method:
//
//http.csrf().disable(): You are turning off CSRF protection. CSRF protection prevents attackers from tricking your users into performing actions on your website without their knowledge. In this case, you are choosing to disable this protection (not recommended for production) for simplicity.
//
//authorizeRequests().anyRequest().authenticated(): You are configuring a rule that says any request to any URL should be allowed only for authenticated (logged in) users. In other words, to access any page on your website, users must log in first.
//
//httpBasic(): You are specifying that you want to use HTTP Basic Authentication. This means when a user tries to access a restricted part of your site, the browser will show a login dialog, and the user needs to enter their username and password.
//
//So, in simple terms, your code is setting up security for your web application. You're turning off CSRF protection (for simplicity, but not recommended in production), and you're requiring users to log in before they can access any part of your site. When they try to access restricted areas, a basic login dialog will appear. This helps ensure that only authorized users can access your web application's protected resources.
//@Override
//@Bean
//protected UserDetailsService userDetailsService() {//this class comes from security library
//    UserDetails user =
//            User.builder().username("pankaj").password(getEncoder()//iam creating 1st objects userr without new keyword ,i will call getencoder method,getencoder will return decrypt object .encodde that will encode the password
//                    .encode("password")).roles("USER").build();
//    UserDetails admin =
//            User.builder().username("admin").password(getEncoder()//iam creating 2nd objects admin without new keyword ,i will call getencoder method,getencoder will return decrypt object .encodde that will encode the password
//                    .encode("admin")).roles("ADMIN").build();
//    return new InMemoryUserDetailsManager(admin, user);

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {//it refers to the context thaat is to be sent to authcontroller sign in method
    auth.userDetailsService(userDetailsService)//iam calling userdetailsservice method and supply userDetailService object
            .passwordEncoder(getEncoder());
}
}