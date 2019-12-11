package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialUsersInsertion {

  private final DbUserRepo repo;
  private final PasswordEncoder enc;

  public InitialUsersInsertion(DbUserRepo repo, PasswordEncoder enc) {
    this.repo = repo;
    this.enc = enc;
  }

  public void createUser() {
    repo.save(
        new DbUser("john", enc.encode("789"), new String[]{"USER", "ADMIN"})
    );
  }


}
