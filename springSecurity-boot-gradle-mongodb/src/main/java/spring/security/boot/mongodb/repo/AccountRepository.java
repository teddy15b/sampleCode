package spring.security.boot.mongodb.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import spring.security.boot.mongodb.domain.Account;

/**
 * @author teddy
 *
 */

public interface AccountRepository extends MongoRepository<Account, String> {
  
  public Account findByUsername(String name);
  
  public Long deleteByUsername(String name);
}
