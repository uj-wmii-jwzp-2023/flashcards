package wmii.jwzp.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.utils.errors.NotFound;
import wmii.jwzp.flashcards.utils.errors.Unauthorized;

@Service()
public class UserGroupLinkService {

  Logger logger = LoggerFactory.getLogger(UserGroupLinkService.class);

  @Autowired()
  private UserGroupLinkRepository userGroupLinkRepository;

  public void verifyUserAction(UserModel user, StudyGroupModel group, int requiredAccess) {
    logger.info("USER ID: " + user.getId() + " GROUP ID: " + group.getId());
    var linkId = new UserGroupLinkModelId();
    linkId.user_id = user.getId();
    linkId.group_id = group.getId();

    var link = userGroupLinkRepository.findById(linkId);

    logger.info(link.get().getGroupId() + " -- " + link.get().getUserId() + " -- " + link.get().getAccessLevel());

    if (!link.isPresent()) {
      throw new Unauthorized("User does not belong to that group");
    }

    if (link.get().getAccessLevel() < requiredAccess) {
      throw new Unauthorized("User is not allowed to perform the action.");
    }
  }

  public UserGroupLinkModel updateAccessLevel(StudyGroupModel group, UserModel user, int access_level) {

    var linkId = new UserGroupLinkModelId();
    linkId.user_id = user.getId();
    linkId.group_id = group.getId();
    var link = userGroupLinkRepository.findById(linkId);
    if (!link.isPresent()) {
      throw new NotFound("User does not belong to that group");
    }
    link.get().setAccessLevel(access_level);
    userGroupLinkRepository.save(link.get());
    return link.get();
  }
}
