package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    private final Candidate candidateOne = new Candidate(0, "Иван", "Java Junior", new byte[10]);
    private final Candidate candidateTwo = new Candidate(0, "Иван", "Java Junior", new byte[10]);

    @Test
    public void whenCreateCandidateAndFindItById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        store.add(candidateOne);
        Candidate candidateInDb = store.findById(candidateOne.getId());
        assertThat(candidateInDb.getName(), is(candidateOne.getName()));
        store.deleteAll();
    }
}