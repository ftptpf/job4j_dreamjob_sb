package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class PostDBStore {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id"), "no name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id"), "no name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, description, created, visible, city_id) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    post.setId(it.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
