package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.util.UserUtil;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        UserUtil.checkAndSetGuestName(model, session);
        return "index";
    }
}
