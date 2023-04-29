package wmii.jwzp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

public interface UserGroupLinkRepository extends JpaRepository<UserGroupLinkModel, UserGroupLinkModelId> {
}