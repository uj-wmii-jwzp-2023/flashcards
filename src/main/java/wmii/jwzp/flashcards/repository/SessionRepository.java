package wmii.jwzp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wmii.jwzp.flashcards.model.db.SessionModel;

public interface SessionRepository extends JpaRepository<SessionModel, String> {
}