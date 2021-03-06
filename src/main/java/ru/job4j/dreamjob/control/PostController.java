package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import ru.job4j.dreamjob.util.UserUtil;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class PostController {
    private final PostService service;
    private final CityService cities;

    public PostController(PostService service, CityService cities) {
        this.service = service;
        this.cities = cities;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        UserUtil.checkAndSetGuestName(model, session);
        model.addAttribute("posts", service.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        UserUtil.checkAndSetGuestName(model, session);
        model.addAttribute("post", new Post(0, "Заполните поле"));
        model.addAttribute("cities", cities.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        City city = cities.findById(cityId);
        post.setCity(city);
        service.add(post);
        return "redirect:/posts";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        City city = cities.findById(cityId);
        post.setCity(city);
        service.update(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        UserUtil.checkAndSetGuestName(model, session);
        model.addAttribute("post", service.findById(id));
        model.addAttribute("cities", cities.getAllCities());
        return "updatePost";
    }
}
