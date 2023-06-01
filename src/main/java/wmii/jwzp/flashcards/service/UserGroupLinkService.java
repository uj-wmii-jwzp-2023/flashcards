package wmii.jwzp.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.utils.errors.Unauthorized;

@Service()
public class UserGroupLinkService {

  @Autowired()
  private UserGroupLinkRepository userGroupLinkRepository;

  public UserGroupLinkModel verifyUserAction(UserModel user, StudyGroupModel group, int requiredAccess) {

    throw new Unauthorized("Not authorized to perform action.");
  }

}
