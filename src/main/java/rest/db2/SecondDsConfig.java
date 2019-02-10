package rest.db2;

import static java.util.Collections.singletonMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "secondEntityManagerFactory",
    transactionManagerRef = "secondTransactionManager", basePackages = "rest.db2")
@EnableTransactionManagement
public class SecondDsConfig {
  @Bean
  @ConfigurationProperties("app.datasource.second")
  public DataSourceProperties secondDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("app.datasource.second.configuration")
  public HikariDataSource secondDataSource() {
    return secondDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class)
        .build();
  }

  @Bean(name = "secondEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
      final EntityManagerFactoryBuilder builder,
      @Qualifier("secondDataSource") DataSource dataSource) {
    return builder.dataSource(secondDataSource()).packages("rest.db2").persistenceUnit("secondDb")
        .properties(singletonMap("hibernate.hbm2ddl.auto", "create-drop")).build();
  }

  @Bean(name = "secondTransactionManager")
  public PlatformTransactionManager secondTransactionManager(
      @Qualifier("secondEntityManagerFactory") EntityManagerFactory secondEntityManagerFactory) {
    return new JpaTransactionManager(secondEntityManagerFactory);
  }
}
