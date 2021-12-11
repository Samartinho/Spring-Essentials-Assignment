package springEssentialsAssignment.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springEssentialsAssignment.main.controller.UserController;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.service.UserService;

import java.util.Optional;


@Configuration
@EnableWebSecurity
public class UserSecurity<ClientDetailsServiceConfigurer> extends WebSecurityConfigurerAdapter {

    @Autowired private UserController controller;

    @Autowired private UserService service;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/", "/logIn", "/add-User", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/logIn").defaultSuccessUrl("/home", true).permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logOff")).logoutSuccessUrl("/").permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean public PasswordEncoder passwordEncoder() { return NoOpPasswordEncoder.getInstance(); }

    @Bean
    public UserDetailsService userDetailsService(){
        return (UserDetailsService) email -> {
            Optional<User> user = service.findUserByEmail(email);
            if(user.isEmpty()) throw new UsernameNotFoundException("No user found with the email:" + email);
            UserPrincipal principal = new UserPrincipal(user.get());
            controller.setUserPrincipal(principal);
            return principal;
        };
    }

}
