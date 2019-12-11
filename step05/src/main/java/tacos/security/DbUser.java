package tacos.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "dbuserabc")
public class DbUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String name;
  private String password;
  private String roles;

  public DbUser(String name, String password, String[] roles) {
    this.name = name;
    this.password = password;
    setRoles(roles);
  }

  public String[] getRoles() {
    return this.roles == null ? new String[]{"USER"} :
        this.roles.split(":");
  }

  public void setRoles(String[] roles) {
    this.roles = String.join(":", roles);
  }
}
