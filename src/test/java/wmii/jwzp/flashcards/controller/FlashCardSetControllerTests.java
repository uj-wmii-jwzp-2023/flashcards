package wmii.jwzp.flashcards.controller;

import wmii.jwzp.flashcards.model.db.CardModel;
import wmii.jwzp.flashcards.model.db.FlashcardSetModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.service.CardService;
import wmii.jwzp.flashcards.service.FlashcardSetService;
import wmii.jwzp.flashcards.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class FlashCardSetControllerTests {

  @Autowired
  FlashcardSetController studyGroupController;

  @MockBean
  private StudyGroupRepository studyGroupRepository;

  @MockBean
  private UserService userService;

  @MockBean
  private FlashcardSetService setService;

  @MockBean
  private CardService cardService;

  @MockBean
  private UserGroupLinkRepository userGroupLinkRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SessionRepository sessionRepository;

  @Autowired
  private MockMvc api;

  @Test
  void getSets_test() throws Exception {
    api.perform(get("/flashcard_sets"))
        .andExpect(status().isOk());
  }

  @Test
  void createSet_test() throws Exception {
    var a = new FlashcardSetModel();
    a.setCreatedAt();
    a.setGroupId("nul");
    a.setId("null");
    a.setIsPublic(true);
    a.setName("null");
    a.setUserId("null");
    Mockito.when(setService.getSet(any())).thenReturn(a);
    Mockito.when(setService.createSet(any(), any())).thenReturn(a);
    api.perform(post("/flashcard_sets")
        .contentType("application/json")
        .cookie(new Cookie("sid", "123"))
        .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
        .andExpect(status().isOk());
  }

  @Test
  void updateSet_test() throws Exception {
    var a = new FlashcardSetModel();
    a.setCreatedAt();
    a.setGroupId("nul");
    a.setId("null");
    a.setIsPublic(true);
    a.setName("null");
    a.setUserId("null");
    Mockito.when(setService.getSet(any())).thenReturn(a);
    Mockito.when(setService.updateSet(any(), any())).thenReturn(a);
    api.perform(patch("/flashcard_sets/id")
        .contentType("application/json")
        .cookie(new Cookie("sid", "123"))
        .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
        .andExpect(status().isOk());
  }

  @Test
  void deleteSet_test() throws Exception {
    var a = new FlashcardSetModel();
    a.setCreatedAt();
    a.setGroupId("nul");
    a.setId("null");
    a.setIsPublic(true);
    a.setName("null");
    a.setUserId("null");
    Mockito.when(setService.getSet(any())).thenReturn(a);
    Mockito.when(setService.removeSet(any())).thenReturn(a);
    api.perform(delete("/flashcard_sets/id")
        .contentType("application/json")
        .cookie(new Cookie("sid", "123"))
        .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
        .andExpect(status().isOk());
  }

  @Test
  void updatecard_test() throws Exception {
    var a = new FlashcardSetModel();
    a.setCreatedAt();
    a.setGroupId("nul");
    a.setId("null");
    a.setIsPublic(true);
    a.setName("null");
    a.setUserId("null");
    CardModel cm = new CardModel();
    Mockito.when(setService.getSet(any())).thenReturn(a);
    Mockito.when(setService.removeSet(any())).thenReturn(a);
    Mockito.when(cardService.updateCard(any(), any())).thenReturn(cm);
    Mockito.when(cardService.getCardById(any())).thenReturn(cm);
    api.perform(delete("/flashcard_sets/id")
        .contentType("application/json")
        .cookie(new Cookie("sid", "123"))
        .content("{\"nick\": \"ok2\",\"password\": \"ok\"}"))
        .andExpect(status().isOk());
  }
}