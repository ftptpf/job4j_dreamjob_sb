package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserDbStoreTest {
    private final User userOne = new User("mail@mail.ru", "password_1");
    private final User userTwo = new User("mail@mail.ru", "password_2");

    @Test
    public void whenCreateUserAndFindItByEmail() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        store.add(userOne);
        User userInDb = store.findUserByEmail(userOne.getEmail());
        assertThat(userInDb.getEmail(), is(userOne.getEmail()));
        store.deleteAll();
    }

    @Test
    public void whenCreateUsersWithSameEmails() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        store.add(userOne);
        store.add(userTwo);
        store.deleteAll();
    }
}