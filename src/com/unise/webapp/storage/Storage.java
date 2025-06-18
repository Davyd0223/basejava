package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {
    void clear();

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    Resume[] getAll();

    int size();
}
