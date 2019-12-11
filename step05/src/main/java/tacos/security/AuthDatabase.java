package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Primary
@Configuration
public class AuthDatabase implements Auth {

  private final DbUserRepo repo;
  private final PasswordEncoder enc;

  public AuthDatabase(DbUserRepo repo, PasswordEncoder enc) {
    this.repo = repo;
    this.enc = enc;
  }

  private Function<DbUser, UserDetails> mapper = u ->
      User.withUsername(u.getName())
          .password(u.getPassword())
          .roles(u.getRoles()).build();

  @Override
  public Collection<UserDetails> content() {
    return StreamSupport.stream(repo.findAll().spliterator(), false)
        .map(mapper).collect(Collectors.toList());
  }
}
