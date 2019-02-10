package rest.db1;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import rest.Account;

@Entity
@Data
public class FirstAccount implements Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private BigDecimal total;

  private BigDecimal current;

}
