package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Вакансия в компании Union", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "Горячая вакансия", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "Пока на паузе. Актуально будет с конца июня.", LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}