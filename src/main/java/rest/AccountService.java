package rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;

import rest.db1.FirstAccountRepository;
import rest.db2.SecondAccountRepository;

@RestController
@RequestMapping(path = "/account")
public class AccountService {
  @Autowired
  private FirstAccountRepository firstAccountRepository;

  @Autowired
  private SecondAccountRepository secondAccountRepository;

  @RequestMapping(method = RequestMethod.GET)
  public HttpEntity<Accounts> get() {
    Accounts accounts = new Accounts(Iterables.concat(
        firstAccountRepository.findAll(), secondAccountRepository.findAll()));
    accounts.add(linkTo(methodOn(AccountService.class).get()).withSelfRel());
    return new ResponseEntity<>(accounts, HttpStatus.OK);
  }

  public class Accounts extends ResourceSupport {

    private final Iterable<Account> content;

    @JsonCreator
    public Accounts(@JsonProperty("content") Iterable<Account> content) {
      this.content = content;
    }

    public Iterable<Account> getContent() {
      return content;
    }
  }
}
