package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;

public class CandidateService {
    private final CandidateStore store = CandidateStore.instOf();
    private static final CandidateService INST = new CandidateService();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }


}
