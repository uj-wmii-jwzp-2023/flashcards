package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.UserModel;
import wmii.jwzp.flashcards.utils.PasswordHash;

@Service
public class UserService {
  private final JdbcTemplate jdbcTemplate;

  public UserService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<UserModel> rowMapper = (rs, rowNum) -> new UserModel(
      rs.getString("id"),
      rs.getString("name"),
      rs.getString("hashedPassword"));

  public List<UserModel> findAll() {
    String findAllUsers = """
        select * from users
        """;
    return jdbcTemplate.query(findAllUsers, rowMapper);
  }

  public String findByName(String name) {
    String findByName = """
        select capital from Users where name = ?;
        """;
    return jdbcTemplate.queryForObject(findByName, String.class, name);
  }

  public void createUser(String name, String password) {
    String hashedPassword = PasswordHash.hash(password);
    String id = UUID.randomUUID().toString();
    String createUser = """
        insert into Users (id, name, hashedPassword) values (?, ?, ?);
        """;
    jdbcTemplate.update(createUser, id, name, hashedPassword);

  }
}