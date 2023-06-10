package wmii.jwzp.flashcards.controller;

import wmii.jwzp.flashcards.model.db.SessionModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
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

@AutoConfigureDataJpa
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
 
}