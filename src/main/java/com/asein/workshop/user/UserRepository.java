package com.asein.workshop.user;

import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.user.dto.UserListResponse;
import com.asein.workshop.user.dto.UserResponse;
import com.asein.workshop.user.dto.UserRowMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream().findFirst();
    }

    public UserResponse save(User user) {
        String sql = """
                insert into dev.users (username, password, full_name, role, is_active, created_at)
                values(?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getRole(),
                user.getIsActive(),
                Timestamp.from(Instant.now())
        );

        return new UserResponse(
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                user.getIsActive()
        );
    }

    public Optional<User> findById(long id) {
        String sql = "SELECT * FROM dev.users WHERE id = ?";

        try {
            User user = jdbcTemplate.queryForObject(
                    sql,
                    new UserRowMapper(),
                    id
            );
            assert user != null;
            return Optional.of(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<UserResponse> update(User u) {
        String sql = """
            UPDATE dev.users
            SET username = ?,
                full_name = ?,
                role = ?,
                is_active = ?
            WHERE id = ?
            """;

        try {
            int rowsAffected = jdbcTemplate.update(
                    sql,
                    u.getUsername(),
                    u.getFullName(),
                    u.getRole(),
                    u.getIsActive(),
                    u.getId()
            );

            if (rowsAffected == 0) {
                return Optional.empty();
            }

            return Optional.of(
                    new UserResponse(
                            u.getUsername(),
                            u.getFullName(),
                            u.getRole(),
                            u.getIsActive()
                    )
            );
        } catch (Exception e) {
            log.error("internal server error: ", e);
            throw new RuntimeException("Internal server error.");
        }
    }

    public Optional<UserResponse> updatePassword(User u) {
        String sql = """
            UPDATE dev.users
            SET password = ?
            WHERE id = ?
            """;

        try {
            int rowsAffected = jdbcTemplate.update(
                    sql,
                    u.getPassword(),
                    u.getId()
            );

            if (rowsAffected == 0) {
                return Optional.empty();
            }

            return Optional.of(
                    new UserResponse(
                            u.getUsername(),
                            u.getFullName(),
                            u.getRole(),
                            u.getIsActive()
                    )
            );
        } catch (DataAccessException e) {
            log.error("internal server error: ", e);
            throw new RuntimeException("Internal server error.");
        }
    }

    public boolean delete(long id) {
        String sql = """
                DELETE FROM dev.users WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<UserListResponse> findAllUsers(
            int offset,
            int size,
            String username,
            String fullName) {

        StringBuilder sql = new StringBuilder("""
            SELECT id, username, full_name, role, is_active
            FROM dev.users
            WHERE 1 = 1
            """);

        List<Object> params = new ArrayList<>();

        if (username != null && !username.isBlank()) {
            sql.append(" AND username = ? ");
            params.add(username);
        }

        if (fullName != null && !fullName.isBlank()) {
            sql.append(" AND full_name = ? ");
            params.add(fullName);
        }

        sql.append("""
            ORDER BY id
            LIMIT ? OFFSET ?
            """);

        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(
                sql.toString(),
                (rs, rowNum) -> new UserListResponse(
                        rs.getLong("id"),
                        new UserResponse(
                                rs.getString("username"),
                                rs.getString("full_name"),
                                rs.getString("role"),
                                rs.getBoolean("is_active")
                        )
                ),
                params.toArray()
        );
    }
}
