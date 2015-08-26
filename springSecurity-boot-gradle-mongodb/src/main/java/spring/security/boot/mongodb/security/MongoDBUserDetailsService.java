package spring.security.boot.mongodb.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.security.boot.mongodb.domain.Account;
import spring.security.boot.mongodb.domain.PasswordChanging;
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
          Optional.of(new User(account.getUsername(), account.getPassword(), account.isEnabled(),
              account.isAccountNonExpired(), account.isCredentialsNonExpired(), account
                  .isAccountNonLocked(), account.getAuthorities()));
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
      try {
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
      } catch (Exception e) {
        logger.error("set account password failed!");
      }
      logger.info("account '{}' has updated.", user.getUsername());
    } else
      logger.error("account '{}' has not existed!", user.getUsername());
  }

  @Override
  public void deleteUser(String username) {
    if (userExists(username)) {
      accountRepo.deleteByUsername(username);
      logger.info("account '{}' has deleted.", username);
    } else
      logger.error("account '{}' has not existed!", username);
  }

  public String changePassword(HttpServletRequest request, String username,
      PasswordChanging passwordChanging) {
    Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
    if (currentUser.getName().equals(username)) {
      if (accountRepo.findByUsername(username).getPassword()
          .equals(passwordChanging.getOldPassword())) {
        if (passwordChanging.getNewPassword().equals(passwordChanging.getConfirmNewPassword())) {
          changePassword(passwordChanging.getOldPassword(), passwordChanging.getNewPassword());
          new SecurityContextLogoutHandler().logout(request, null, null);
          return "password has changed.";
        } else
          return "new password and confirm new password are not match!";
      }
      return "current password is incorrect!";
    } else
      return "cannot access other accounts!";
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
    String username = currentUser.getName();

    Account account = accountRepo.findByUsername(username);
    try {
      account.setPassword(newPassword);
      accountRepo.save(account);
      logger.info("account '{}' password has changed.", account.getUsername());
    } catch (Exception e) {
      logger.error("set account password failed!");
    }

    // if u want to keep login, update SecurityContext
    // SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser,
    // newPassword));
  }

  protected Authentication createNewAuthentication(Authentication currentUser, String newPassword) {
    UserDetails user = loadUserByUsername(currentUser.getName());

    UsernamePasswordAuthenticationToken newAuthentication =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    newAuthentication.setDetails(currentUser.getDetails());

    return newAuthentication;
  }

  @Override
  public boolean userExists(String username) {
    if (Optional.ofNullable(accountRepo.findByUsername(username)).isPresent())
      return true;
    else
      return false;
  }

}
