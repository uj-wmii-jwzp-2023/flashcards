package wmii.jwzp.flashcards.model.api.output;

import java.util.List;
import java.util.stream.Collectors;

import wmii.jwzp.flashcards.model.db.GroupAchievementModel;

public class GroupAchievementResponse {
  public List<String> enabledAchievements;
  public List<String> disabledAchievements;

  public GroupAchievementResponse(List<GroupAchievementModel> groupAchievements, List<String> allAchievements) {
    this.enabledAchievements = groupAchievements.stream().map(e -> e.getName()).collect(Collectors.toList());
    this.disabledAchievements = allAchievements.stream().filter(e -> !this.enabledAchievements.contains(e))
        .collect(Collectors.toList());
  }
}
