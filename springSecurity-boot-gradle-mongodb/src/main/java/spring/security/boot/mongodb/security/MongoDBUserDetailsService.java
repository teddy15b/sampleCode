package spring.security.boot.mongodb.security;

import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
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

  @Autowired
  private AccountRepository accountRepo;

  @Resource(name = "objectMapper")
  private ObjectMapper om;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserDetails> loadedUser;
    try {
      Account account = accountRepo.findByUsername(username);
      System.out.println(account.toString());
      loadedUser =
          Optional.of(new User(account.getUsername(), account.getPassword(), account.getAuthorities() ));
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
    // TODO Auto-generated method stub

  }

  @Override
  public void updateUser(UserDetails user) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteUser(String username) {
    // TODO Auto-generated method stub
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean userExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }


}
