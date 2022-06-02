package ru.job4j.dreamjob.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
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
        PostService service = mock(PostService.class);
        when(service.findAll()).thenReturn(posts);
        CityService cities = mock(CityService.class);
        PostController postController = new PostController(service, cities);
        HttpSession session = mock(HttpSession.class);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }
}