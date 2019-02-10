package rest;

import java.math.BigDecimal;

import org.springframework.hateoas.Identifiable;

public interface Account extends Identifiable<Long>{
  BigDecimal getCurrent();

  String getName();

  BigDecimal getTotal();
}
