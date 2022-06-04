package ru.job4j.dreamjob.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Test
    public void whenPosts() {
        Collection<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        when(postService.findAll()).thenReturn(posts);

        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPost() {
        Post post = new Post(0, "Заполните поле");
        Collection<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Калининград")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        when(cityService.getAllCities()).thenReturn(cities);

        String page = postController.addPost(model, session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", null, LocalDateTime.now(), new City(1, "no name"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        when(cityService.findById(any(int.class))).thenReturn(new City(1, "Москва"));

        String page = postController.createPost(input);
        verify(cityService).findById(input.getCity().getId());
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Post post = new Post(1, "Some post", "information", localDateTime, new City(2, "no name"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        String page = postController.updatePost(post);
        verify(postService).update(post);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "New post");
        Collection<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Калининград")
        );
        int userId = 1;
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        when(postService.findById(any(int.class))).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(cities);

        String page = postController.formUpdatePost(model, userId, session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("updatePost"));
    }
}