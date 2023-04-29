package wmii.jwzp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;

public interface StudyGroupRepository extends JpaRepository<StudyGroupModel, String> {
}