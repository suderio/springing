package rest.db1;

import static java.util.Collections.singletonMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "firstEntityManagerFactory", transactionManagerRef = "firstTransactionManager", basePackages = "rest.db1")
@EnableTransactionManagement
public class FirstDsConfig {
  @Primary
  @Bean
  @ConfigurationProperties("app.datasource.first")
  public DataSourceProperties firstDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean
  @ConfigurationProperties("app.datasource.first.configuration")
  public HikariDataSource firstDataSource() {
    return firstDataSourceProperties().initializeDataSourceBuilder()
        .type(HikariDataSource.class).build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("firstDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("rest.db1")
        .persistenceUnit("firstDb")
        .properties(singletonMap("hibernate.hbm2ddl.auto", "create-drop"))
        .build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager firstTransactionManager(
      @Qualifier("firstEntityManagerFactory") EntityManagerFactory firstEntityManagerFactory) {
    return new JpaTransactionManager(firstEntityManagerFactory);
  }
}
