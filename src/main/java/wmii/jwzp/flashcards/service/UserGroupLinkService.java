package wmii.jwzp.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.LinkUpdateInput;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.utils.AccessLevels;
import wmii.jwzp.flashcards.utils.errors.BadRequest;
import wmii.jwzp.flashcards.utils.errors.NotFound;
import wmii.jwzp.flashcards.utils.errors.Unauthorized;

@Service()
public class UserGroupLinkService {

  Logger logger = LoggerFactory.getLogger(UserGroupLinkService.class);

  @Autowired()
  private UserGroupLinkRepository userGroupLinkRepository;

  public void verifyUserAction(UserModel user, StudyGroupModel group, int requiredAccess) {
    logger.info("USER ID: " + user.getId() + " GROUP ID: " + group.getId());
    if (user == null || group == null) {
      throw new Unauthorized("Unauthorized");
    }
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

  public UserGroupLinkModel joinGroup(StudyGroupModel group, UserModel user, int accessLevel) {
    var userGroupLink = new UserGroupLinkModel();
    userGroupLink.setUserId(user.getId());
    userGroupLink.setGroupId(group.getId());
    userGroupLink.setCreatedAt();
    userGroupLink.setAccessLevel(accessLevel);
    userGroupLinkRepository.save(userGroupLink);
    return userGroupLink;
  }

  public UserGroupLinkModel leaveGroup(StudyGroupModel group, UserModel user) {
    var link = userGroupLinkRepository.getById(user.getId(), group.getId());
    var groupLinks = userGroupLinkRepository.findByGroupId(group.getId());
    var adminCount = groupLinks.stream().filter(e -> e.getAccessLevel() == AccessLevels.ADMIN).count();
    if (adminCount == 1 && link.get().getAccessLevel() == AccessLevels.ADMIN) {
      throw new BadRequest("User is the only admin and cannot be removed.");
    }
    userGroupLinkRepository.delete(link.get());
    return link.get();
  }

  public UserGroupLinkModel updateLink(StudyGroupModel group, UserModel user, LinkUpdateInput input) {
    if (input.access_level == null) {
      throw new BadRequest("Invalid input");

    }
    var linkId = new UserGroupLinkModelId();
    linkId.user_id = user.getId();
    linkId.group_id = group.getId();
    var link = userGroupLinkRepository.findById(linkId);
    if (!link.isPresent()) {
      throw new NotFound("User does not belong to that group");
    }
    var groupLinks = userGroupLinkRepository.findByGroupId(group.getId());
    var adminCount = groupLinks.stream().filter(e -> e.getAccessLevel() == AccessLevels.ADMIN).count();
    if (adminCount == 1 && link.get().getAccessLevel() == AccessLevels.ADMIN
        && input.access_level != AccessLevels.ADMIN) {
      throw new BadRequest("User is the only admin and cannot be removed.");
    }

    link.get().setAccessLevel(input.access_level);
    userGroupLinkRepository.save(link.get());
    return link.get();
  }
}
