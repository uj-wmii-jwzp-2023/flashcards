package wmii.jwzp.flashcards.controller;

import wmii.jwzp.flashcards.model.db.SessionModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.PasswordHash;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class UsersControllerTests {

  @Autowired
  UserController userController;


  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SessionRepository sessionRepository;

  @Autowired
  private MockMvc api;

  @Test
  void shouldRegisterNewUser() throws Exception {
    Mockito.when(userRepository.existsByNick(anyString())).thenReturn(false);
    api.perform(post("/users")
      .contentType("application/json")
      .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
      .andExpect(status().isOk());
  }
  @Test
  void userExistsCreationFails() throws Exception {
    Mockito.when(userRepository.existsByNick(anyString())).thenReturn(true);
    api.perform(post("/users")
      .contentType("application/json")
      .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
      .andExpect(status().is4xxClientError());
  }
  @Test
  void loginUser() throws Exception {
    UserModel retuserModel = new UserModel();
    retuserModel.setHashedPassword(PasswordHash.hash("ok2"));
    Mockito.when(userRepository.findByNick(anyString())).thenReturn(retuserModel);
    
    api.perform(post("/users/login")
      .header("Authorization", "Basic b2s6b2sy"))//login: ok password: ok2
      .andExpect(status().isOk());
  }
  
  
  @Test
  void failLoginWrongCredentials() throws Exception {
    UserModel retuserModel = new UserModel();
    retuserModel.setHashedPassword(PasswordHash.hash("wrong_password"));
    Mockito.when(userRepository.findByNick(anyString())).thenReturn(retuserModel);
    
    api.perform(post("/users/login")
      .header("Authorization", "Basic b2s6b2sy"))//login: ok password: ok2
      .andExpect(status().is4xxClientError());
    }
    
  @Test
  void refresSuccess() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    Optional<SessionModel> optionalSessionModel = Optional.of(sessionModel);
    Mockito.when(sessionRepository.findById(anyString())).thenReturn(optionalSessionModel);
    
    api.perform(post("/users/session")
      .cookie(new Cookie("sid","123")))
      .andExpect(status().isOk());
  }

  @Test
  void refresSuccessFailNoSession() throws Exception {
    Optional<SessionModel> optionalSessionModel = Optional.empty();
    Mockito.when(sessionRepository.findById(anyString())).thenReturn(optionalSessionModel);
    
    api.perform(post("/users/session")
      .cookie(new Cookie("sid","123")))
      .andExpect(status().is4xxClientError());
  }
  
}