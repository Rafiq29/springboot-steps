package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AuthHashMap implements Auth {

  private final Map<String, String> storage = new HashMap<>();
  private final PasswordEncoder enc;

  public AuthHashMap(PasswordEncoder encoder) {
    this.enc = encoder;
    storage.put("jim", "123");
    storage.put("john", "456");
  }

  private Function<Map.Entry<String, String>, UserDetails> mapper = new Function<Map.Entry<String, String>, UserDetails>() {
    @Override
    public UserDetails apply(Map.Entry<String, String> me) {
      return User.withUsername(me.getKey())
          .password(me.getValue())
          .passwordEncoder(enc::encode)
          .roles("USER").build();
    }
  };

  @Override
  public Collection<UserDetails> content() {
    return storage.entrySet().stream().map(mapper).collect(Collectors.toList());
  }
}
