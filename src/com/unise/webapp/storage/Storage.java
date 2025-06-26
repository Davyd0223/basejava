package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {

    void save(Resume r);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    Resume[] getAll();

    int size();

    void clear();
}
