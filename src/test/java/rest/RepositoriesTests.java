package rest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import rest.db1.FirstAccountRepository;
import rest.db2.SecondAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RepositoriesTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private FirstAccountRepository firstAccountRepository;

  @Autowired
  private SecondAccountRepository secondAccountRepository;

  @Before
  public void deleteAllBeforeTests() throws Exception {
    firstAccountRepository.deleteAll();
    secondAccountRepository.deleteAll();
  }

  @Test
  public void shouldReturnRepositoryIndex() throws Exception {

    mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$._links.account1").exists())
        .andExpect(jsonPath("$._links.account2").exists());

  }

  @Test
  public void shouldCreateEntity() throws Exception {

    mockMvc
        .perform(post("/account1")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("account1/")));

    mockMvc
        .perform(post("/account2")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("account2/")));
  }

  @Test
  public void shouldRetrieveEntity() throws Exception {

    MvcResult mvcResult = mockMvc
        .perform(post("/account1")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Rick"))
        .andExpect(jsonPath("$.total").value("100.0"));

    mvcResult = mockMvc
        .perform(post("/account2")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Rick"))
        .andExpect(jsonPath("$.total").value("100.0"));
  }

  @Test
  public void shouldQueryEntity() throws Exception {

    mockMvc
        .perform(post("/account1")
            .content("{ \"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated());

    mockMvc.perform(get("/account1/search/findByName?name={name}", "Rick"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.account1[0].total").value("100.0"));

    mockMvc
        .perform(post("/account2")
            .content("{ \"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated());

    mockMvc.perform(get("/account2/search/findByName?name={name}", "Rick"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.account2[0].total").value("100.0"));
  }

  @Test
  public void shouldUpdateEntity() throws Exception {

    MvcResult mvcResult = mockMvc
        .perform(post("/account1")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc
        .perform(
            put(location).content("{\"name\": \"Morty\", \"total\":\"500.0\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Morty"))
        .andExpect(jsonPath("$.total").value("500.0"));

    mvcResult = mockMvc
        .perform(post("/account2")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    location = mvcResult.getResponse().getHeader("Location");

    mockMvc
        .perform(
            put(location).content("{\"name\": \"Morty\", \"total\":\"500.0\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Morty"))
        .andExpect(jsonPath("$.total").value("500.0"));
  }

  @Test
  public void shouldPartiallyUpdateEntity() throws Exception {

    MvcResult mvcResult = mockMvc
        .perform(post("/account1")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(patch(location).content("{\"current\": \"10.0\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.current").value("10.0"))
        .andExpect(jsonPath("$.total").value("100.0"));

    mvcResult = mockMvc
        .perform(post("/account2")
            .content("{\"name\": \"Rick\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(patch(location).content("{\"current\": \"10.0\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk())
        .andExpect(jsonPath("$.current").value("10.0"))
        .andExpect(jsonPath("$.total").value("100.0"));
  }

  @Test
  public void shouldDeleteEntity() throws Exception {

    MvcResult mvcResult = mockMvc
        .perform(post("/account1")
            .content("{ \"name\": \"Morty\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(delete(location)).andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isNotFound());

    mvcResult = mockMvc
        .perform(post("/account2")
            .content("{ \"name\": \"Morty\", \"total\":\"100.0\"}"))
        .andExpect(status().isCreated()).andReturn();

    location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(delete(location)).andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isNotFound());
  }
}