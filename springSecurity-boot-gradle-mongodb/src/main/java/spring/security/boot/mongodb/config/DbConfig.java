package spring.security.boot.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/**
 * @author teddy
 *
 */

@Configuration
@EnableMongoRepositories(basePackages = "spring.security.boot.mongodb.repo")
public class DbConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  public Mongo mongo() throws Exception {
    MongoClient client = new MongoClient("127.0.0.1");
    client.setWriteConcern(WriteConcern.SAFE);
    return client;
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
      return new MongoTemplate(mongo(), getDatabaseName());
  }

}
