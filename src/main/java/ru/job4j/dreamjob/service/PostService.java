package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.CityStore;
import ru.job4j.dreamjob.persistence.PostDBStore;

import java.util.Collection;

@Service
@ThreadSafe
public class PostService {
    private final PostDBStore store;
    private final CityService cityService = new CityService(new CityStore());

    private PostService(PostDBStore store) {
        this.store = store;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())));
        return posts;
    }

    public Post findById(int id) {
        Post post = store.findById(id);
        post.setCity(cityService.findById(post.getCity().getId()));
        return store.findById(id);
    }

    public void add(Post post) {
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}
