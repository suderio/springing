package rest;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;

	private BigDecimal total;

  private BigDecimal current;

  public BigDecimal getCurrent() {
		return current;
	}
	public String getName() {
    return name;
  }

	public BigDecimal getTotal() {
		return total;
	}

	public void setCurrent(BigDecimal lastName) {
		this.current = lastName;
	}

	public void setName(String name) {
    this.name = name;
  }

	public void setTotal(BigDecimal firstName) {
		this.total = firstName;
	}
}
