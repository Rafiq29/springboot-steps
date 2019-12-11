package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecuritySettings extends WebSecurityConfigurerAdapter {

  public MySecuritySettings(InitialUsersInsertion uc) {
    uc.createUser();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
//          .csrfTokenRepository()
//          .and()
        .disable()
        .cors()
          .and()
        .authorizeRequests()
        .antMatchers("/h2-console/**").permitAll()
//        .antMatchers("/u/**").permitAll()
//        .antMatchers("/", "/home", "/about", "/resources/**").permitAll()
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .antMatchers("/user/**").hasRole("USER")
//        .antMatchers(HttpMethod.GET, "/api").permitAll()
//        .antMatchers(HttpMethod.POST, "/api").hasAnyAuthority("USER", "ADMIN")
//        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/login7") // you can write your own
//          .usernameParameter("...")
//          .passwordParameter("...")
//          .successForwardUrl("...")
//          .failureForwardUrl("...")
//          .failureHandler(null)
        .permitAll()
        .and()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/logout-success")
        .permitAll();
    http
      .headers().frameOptions().disable();
  }

  @Bean
  public UserDetailsService myUserDetailsService(Auth auth) {
    return new InMemoryUserDetailsManager(auth.content());
  }
//  @Bean
//  public UserDetailsService myUserDetailsService() {
//    return new InMemoryUserDetailsManager(Arrays.asList(
//        User.withDefaultPasswordEncoder().username("jim").password("123").roles("USER").build(),
//        User.withDefaultPasswordEncoder().username("mario").password("456").roles("USER", "ADMIN").build()
//    ));
//  }
}
