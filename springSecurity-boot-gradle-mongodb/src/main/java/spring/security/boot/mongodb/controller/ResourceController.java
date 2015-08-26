package spring.security.boot.mongodb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.security.boot.mongodb.domain.Account;
import spring.security.boot.mongodb.domain.PasswordChanging;
import spring.security.boot.mongodb.repo.AccountRepository;
import spring.security.boot.mongodb.security.MongoDBUserDetailsService;

/**
 * @author teddy
 *
 */

@RestController
public class ResourceController {
  
  @Autowired
  private AccountRepository accountRepo;
  
  @Autowired
  private MongoDBUserDetailsService userDetailsService;

  @RequestMapping("/resource")
  public Account home(@AuthenticationPrincipal UserDetails userDetails) {
    return accountRepo.findByUsername(userDetails.getUsername());
  }
  
  @RequestMapping(value = "/password/{username}", method=RequestMethod.PATCH)
  public String changePwd(HttpServletRequest request, @PathVariable String username, @RequestBody PasswordChanging passwordChanging) {
    return userDetailsService.changePassword(request, username, passwordChanging);
  }
  
  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/resource", method=RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Account createUser(@RequestBody Account account) {
    userDetailsService.createUser(account);
    return account;
  }
  
  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/resource/{username}", method=RequestMethod.PATCH)
  @ResponseStatus(HttpStatus.OK)
  public String updateUser(@PathVariable String username, @RequestBody Account account) {
    userDetailsService.updateUser(account);
    return account.getUsername() + " has been updated";
  }
  
  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/resource/{username}", method=RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public String removeUser(@PathVariable String username) {
    userDetailsService.deleteUser(username);
    return username + " has been deleted!";
  }
}
