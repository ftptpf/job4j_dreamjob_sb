package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Сергей", "JAVA, SQL, Spring", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Виктор", "PM", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Ольга", "менеджер", LocalDateTime.now()));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void add(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
            candidate.setCreated(LocalDateTime.now());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public void update(Candidate candidate) {
        add(candidate);
    }
}
