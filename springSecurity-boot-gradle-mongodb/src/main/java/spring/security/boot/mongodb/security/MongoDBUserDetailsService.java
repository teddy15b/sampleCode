package spring.security.boot.mongodb.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.boot.mongodb.domain.Account;
import spring.security.boot.mongodb.repo.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author teddy
 *
 */

@Service
@Transactional
public class MongoDBUserDetailsService implements UserDetailsManager, GroupManager {

  public static final Logger logger = LoggerFactory.getLogger(MongoDBUserDetailsService.class);

  @Autowired
  private AccountRepository accountRepo;

  @Resource(name = "objectMapper")
  private ObjectMapper om;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserDetails> loadedUser;
    try {
      Account account = accountRepo.findByUsername(username);
      loadedUser =
          Optional.of(new User(account.getUsername(), account.getPassword(), account
              .getAuthorities()));
      if (!loadedUser.isPresent()) {
        throw new InternalAuthenticationServiceException(
            "UserDetailsService returned null, which is an interface contract violation");
      }
    } catch (Exception repositoryProblem) {
      throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(),
          repositoryProblem);
    }

    return loadedUser.get();
  }

  @Override
  public List<String> findAllGroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> findUsersInGroup(String groupName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void createGroup(String groupName, List<GrantedAuthority> authorities) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteGroup(String groupName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void renameGroup(String oldName, String newName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addUserToGroup(String username, String group) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeUserFromGroup(String username, String groupName) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<GrantedAuthority> findGroupAuthorities(String groupName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addGroupAuthority(String groupName, GrantedAuthority authority) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
    // TODO Auto-generated method stub

  }

  @Override
  public void createUser(UserDetails user) {
    if (!userExists(user.getUsername())) {
      accountRepo.insert((Account) user);
      logger.info("account '{}' has created.", user.getUsername());
    } else
      logger.error("account '{}' has existed!");
  }

  @Override
  public void updateUser(UserDetails user) {
    if (userExists(user.getUsername())) {
      Account account = accountRepo.findByUsername(user.getUsername());
      account.setUsername(user.getUsername());
      account.setPassword(user.getPassword());
      account.setAccountNonExpired(user.isAccountNonExpired());
      account.setAccountNonLocked(user.isAccountNonLocked());
      account.setCredentialsNonExpired(user.isCredentialsNonExpired());
      account.setEnabled(user.isEnabled());
      List<String> roles = new ArrayList<String>();
      user.getAuthorities().forEach(authority -> {
        roles.add(authority.getAuthority());
      });
      account.setRoles(roles);
      accountRepo.save(account);
      logger.info("account '{}' has updated.", user.getUsername());
    } else
      logger.error("account '{}' has not existed!");
  }

  @Override
  public void deleteUser(String username) {
    if (userExists(username)) {
      accountRepo.deleteByUsername(username);
      logger.info("account '{}' has deleted", username);
    } else
      logger.error("account '{}' has not existed!", username);
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean userExists(String username) {
    if (Optional.ofNullable(accountRepo.findByUsername(username)).isPresent())
      return true;
    else
      return false;
  }

}
