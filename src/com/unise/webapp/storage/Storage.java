package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {

    int size();

    void save(Resume r);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    Resume[] getAll();

    void clear();
}
