package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void save(Resume r);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    List<Resume> getAllSorted();

    void clear();
}
