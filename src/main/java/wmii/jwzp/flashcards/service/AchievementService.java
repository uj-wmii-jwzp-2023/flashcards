package wmii.jwzp.flashcards.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.achievements.AchievementFactory;
import wmii.jwzp.flashcards.model.db.GroupAchievementModel;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserAchievementModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.GroupAchievementRepository;
import wmii.jwzp.flashcards.repository.UserAchievementRepository;
import wmii.jwzp.flashcards.utils.errors.BadRequest;

@Service
public class AchievementService {

  Logger logger = LoggerFactory.getLogger(CardService.class);

  @Autowired
  private AchievementFactory achievementFactory;

  @Autowired
  private GroupAchievementRepository groupAchievementRepository;

  @Autowired
  private UserAchievementRepository userAchievementRepository;

  public GroupAchievementModel enableAchievement(StudyGroupModel groupModel, String achievementName) {
    if (!achievementFactory.verifyAchievement(achievementName)) {
      throw new BadRequest("Achievement not available.");
    }

    var existingAchievement = groupAchievementRepository.getById(groupModel.getId(), achievementName);
    if (existingAchievement.isPresent()) {
      return existingAchievement.get();
    }

    var achievement = new GroupAchievementModel();
    achievement.setGroupId(groupModel.getId());
    achievement.setName(achievementName);

    groupAchievementRepository.save(achievement);
    return achievement;
  }

  public GroupAchievementModel disableAchievement(StudyGroupModel groupModel, String achievementName) {
    if (!achievementFactory.verifyAchievement(achievementName)) {
      throw new BadRequest("Achievement not available.");
    }

    var achievement = groupAchievementRepository.getById(groupModel.getId(), achievementName);
    if (!achievement.isPresent()) {
      return null;
    }

    groupAchievementRepository.delete(achievement.get());
    return achievement.get();
  }

  public List<GroupAchievementModel> getAchievements(StudyGroupModel groupModel) {
    var achievements = groupAchievementRepository.findAllByGroupId(groupModel.getId());
    return achievements;
  }

  public List<UserAchievementModel> getAchievements(UserModel userModel) {
    var achievements = userAchievementRepository.findAllByUserId(userModel.getId());
    return achievements;
  }

  public List<UserAchievementModel> getAchievements(StudyGroupModel groupModel, UserModel userModel) {
    var achievements = userAchievementRepository.findAllByGroupIdUserId(groupModel.getId(), userModel.getId());
    return achievements;
  }

}