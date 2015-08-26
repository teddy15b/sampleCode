package spring.security.boot.mongodb.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import spring.security.boot.mongodb.security.AesService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author teddy
 *
 */
@Document(collection = "users")
public class Account implements UserDetails, CredentialsContainer {
  public static final Logger logger = LoggerFactory.getLogger(Account.class);
  private static final long serialVersionUID = -2486911392546018078L;
  @JsonIgnore
  @JsonProperty("_id")
  private String id;
  private String username;
  private String password;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
  private List<String> roles;
 
  @JsonIgnore
  private String sKey = "1234567890abcdef";
  
  public Account() {}

  public Account(String username, String password, List<String> roles) throws Exception {
    this(username, password, roles, true, true, true, true);
  }

  public Account(String username, String password, List<String> roles, boolean accountNonExpired,
      boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) throws Exception {
    this.username = username;
    this.password = AesService.Encrypt(password, sKey).get();
    this.roles = roles;
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
    this.enabled = enabled;
  }

  @JsonIgnore  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    Objects.requireNonNull(password);
    Optional<String> decryptPass = Optional.empty();
    try {
      decryptPass = AesService.Decrypt(password, sKey);
    } catch (Exception e) {
      logger.error("decrypt password failed!");
    }
    return decryptPass.get();
  }

  public void setPassword(String password) throws Exception {
    Objects.requireNonNull(password);
    try {
      this.password = AesService.Encrypt(password, sKey).get();
    } catch (Exception e) {
      throw new Exception("encrypt password failed!");
    }
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public List<String> getRoles() {
    return roles;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("username", username).append("password", password)
        .append("roles", roles).append("accountNonExpired", accountNonExpired)
        .append("accountNonLocked", accountNonLocked)
        .append("credentialsNonExpired", credentialsNonExpired).append("enabled", enabled)
        .toString();
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    this.roles.forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.toString()));
    });
    return authorities;
  }

  @Override
  public void eraseCredentials() {
    password = null;
  }

}

