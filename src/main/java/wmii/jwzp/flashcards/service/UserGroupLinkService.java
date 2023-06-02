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

  public void verifyUserAction(UserModel user, StudyGroupModel group, int requiredAccess) {
    System.out.println("--------------------------");
    System.out.println("--------------------------");
    System.out.println("--------------------------");
    System.out.println("--------------------------");
    System.out.println("--------------------------");
    System.out.println("--------------------------");
    System.out.println("USER ID: " + user.getId() + " GROUP ID: " + group.getId());
    var link = userGroupLinkRepository.getById(user.getId(), group.getId());
    System.out.println("link: " + link.toString());

    if (link == null || link.getAccessLevel() < requiredAccess) {
      throw new Unauthorized("User is not allowed to perform the action.");
    }
  }

  public UserGroupLinkModel updateAccessLevel(StudyGroupModel group, UserModel user, int access_level) {
    var link = userGroupLinkRepository.getById(user.getId(), group.getId());
    link.setAccessLevel(access_level);
    userGroupLinkRepository.save(link);
    return link;
  }
}
