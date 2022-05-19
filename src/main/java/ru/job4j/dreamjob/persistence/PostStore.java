package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Вакансия в компании Union", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "Горячая вакансия", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "Пока на паузе. Актуально будет с конца июня.", LocalDateTime.now()));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void add(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
            post.setCreated(LocalDateTime.now());
        }
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        add(post);
    }
}
