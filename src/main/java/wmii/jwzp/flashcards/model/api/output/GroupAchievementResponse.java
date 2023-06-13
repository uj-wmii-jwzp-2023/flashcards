package wmii.jwzp.flashcards.model.api.output;

import java.util.List;
import java.util.stream.Collectors;

import wmii.jwzp.flashcards.model.db.GroupAchievementModel;

public class GroupAchievementResponse {
  public List<String> achievements;

  public GroupAchievementResponse(List<GroupAchievementModel> groupAchievements) {
    this.achievements = groupAchievements.stream().map(e -> e.getName()).collect(Collectors.toList());
  }
}
