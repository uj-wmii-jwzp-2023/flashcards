package wmii.jwzp.flashcards.controller;

import wmii.jwzp.flashcards.model.api.input.LinkUpdateInput;
import wmii.jwzp.flashcards.model.api.input.StudyGroupInput;
import wmii.jwzp.flashcards.model.api.output.SessionResponse;
import wmii.jwzp.flashcards.model.db.SessionModel;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.service.AchievementService;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class StudyGroupControllerTests {

  @Autowired
  StudyGroupController studyGroupController;

  @MockBean
  private StudyGroupRepository studyGroupRepository;
  
  @MockBean
  private AchievementService achievementService;
  @MockBean
  private UserGroupLinkRepository userGroupLinkRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SessionRepository sessionRepository;

  @Autowired
  private MockMvc api;
  
  @Test
  void getGroups_test() throws Exception {
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
  
  @Test
  void createGroup_test() throws Exception {
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
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.createGroup(sgi,"sid=token").getStatusCode().is2xxSuccessful());
  }

  @Test
  void getGroup_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.getGroup("id","token").getStatusCode().is2xxSuccessful());
  }
  @Test
  void updateGroup_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    userGroupLinkModel.setAccessLevel(420);
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.deleteGroup("id","token").getStatusCode().is2xxSuccessful());
  }
  @Test
  void joinGroup_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    userGroupLinkModel.setAccessLevel(420);
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.joinGroup("id","token").getStatusCode().is2xxSuccessful());
  }
  @Test
  void getUsersInGroup_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    userGroupLinkModel.setAccessLevel(420);
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.getUsersInGroup("id","token").getStatusCode().is2xxSuccessful());
  }
  @Test
  void editUserRoleGroup_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    userGroupLinkModel.setAccessLevel(420);
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.editUserRole("d","ok","token",new LinkUpdateInput()).getStatusCode().is2xxSuccessful());
  }
  @Test
  void disableAchievement_test() throws Exception {
    SessionModel sessionModel = new SessionModel();
    sessionModel.setCreatedAt();
    sessionModel.setExpiresAt();
    sessionModel.setUserId("id");

    UserModel userModel = new UserModel();
    userModel.setCreatedAt();
    userModel.setId("id");
    userModel.setNick("nick");
    
    UserGroupLinkModel userGroupLinkModel = new UserGroupLinkModel();
    userGroupLinkModel.setAccessLevel(420);
    StudyGroupModel studyGroupModel = new StudyGroupModel();

    Mockito.when(sessionRepository.findById(any())).thenReturn(Optional.of(sessionModel));
    Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
    Mockito.when(studyGroupRepository.findById(any())).thenReturn(Optional.of(studyGroupModel));
    Mockito.when(userGroupLinkRepository.save(any())).thenReturn(null);
    Mockito.when(userGroupLinkRepository.findById(any())).thenReturn(Optional.of(userGroupLinkModel));
    StudyGroupInput sgi = new StudyGroupInput();
    sgi.setName("name");

    assertTrue(studyGroupController.disableAchievement("d","ok","achievement").getStatusCode().is2xxSuccessful());
  }
}