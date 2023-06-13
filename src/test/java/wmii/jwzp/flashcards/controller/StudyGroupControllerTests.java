package wmii.jwzp.flashcards.controller;

import wmii.jwzp.flashcards.model.api.output.SessionResponse;
import wmii.jwzp.flashcards.model.db.SessionModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class StudyGroupControllerTests {

  @Autowired
  StudyGroupController studyGroupController;

  @MockBean
  private StudyGroupRepository groupRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SessionRepository sessionRepository;

  @Autowired
  private MockMvc api;
  
  @Test
  void getGroups() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");
    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    Mockito.when(sessionRepository.findById(anyString())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(userModel));
    api.perform(get("/study_groups")
      .cookie(new Cookie("sid","123")))
      .andExpect(status().isOk());
  }


}