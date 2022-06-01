package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDbStore;

@Service
@ThreadSafe
public class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public User findUserByEmail(String email) {
        return store.findUserByEmail(email);
    }

    public User findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }

    public User add(User user) {
        return store.add(user);
    }
}
