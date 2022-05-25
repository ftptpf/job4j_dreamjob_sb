package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    private final City cityOne = new City(1, "city 1");
    private final City cityTwo = new City(2, "city 2");
    private final Post postOne = new Post(0, "Java Job", "Spring Boot", LocalDateTime.now(), true, cityOne);
    private final Post postTwo = new Post(0, "QA", "Automations", LocalDateTime.now(), false, cityTwo);

      @Test
    public void whenCreatePostAndFindItById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(postOne);
        Post postInDb = store.findById(postOne.getId());
        assertThat(postInDb.getName(), is(postOne.getName()));
        store.deleteAll();
    }

    @Test
    public void whenCreateTwoPostsAndFindThemAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        assertThat(store.findAll().size(), is(0));
        store.add(postOne);
        store.add(postTwo);
        assertThat(store.findAll().size(), is(2));
        store.deleteAll();
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    public void whenCreatePostUpdateItAndFindItById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(postOne);
        int id = postOne.getId();
        Post postInDb = store.findById(id);
        assertThat(postInDb.getName(), is(postOne.getName()));
        postTwo.setId(id);
        store.update(postTwo);
        Post postInDbAfterUpdate = store.findById(id);
        assertThat(postInDbAfterUpdate.getName(), is(postTwo.getName()));
        store.deleteAll();
    }
}