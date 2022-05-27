package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    private final Candidate candidateOne = new Candidate(0, "Иван", "Java Junior", new byte[10]);
    private final Candidate candidateTwo = new Candidate(0, "Иван", "Java Junior", new byte[10]);

    @Test
    public void whenCreateCandidateAndFindItById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        store.add(candidateOne);
        Optional<Candidate> candidateInDbOp = store.findAll().stream().findFirst();
        int id = candidateInDbOp.get().getId();
        Candidate candidateInDb = store.findById(id);
        assertThat(candidateInDb.getName(), is(candidateOne.getName()));
        store.deleteAll();
    }

    @Test
    public void whenCreateTwoCandidatesAndFindThemAll() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        assertThat(store.findAll().size(), is(0));
        store.add(candidateOne);
        store.add(candidateTwo);
        assertThat(store.findAll().size(), is(2));
        store.deleteAll();
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    public void whenCreateCandidateUpdateItAndFindItById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        store.add(candidateOne);
        Optional<Candidate> candidateInDbOp = store.findAll().stream().findFirst();
        Candidate candidateInDB = candidateInDbOp.get();
        int id = candidateInDB.getId();
        assertThat(store.findById(id).getName(), is(candidateOne.getName()));
        candidateTwo.setId(id);
        store.update(candidateTwo);
        assertThat(store.findById(id).getName(), is(candidateTwo.getName()));
        store.deleteAll();
    }
}