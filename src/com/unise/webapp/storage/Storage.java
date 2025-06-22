package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {

    void save(Resume r);

    Resume get(String uuid);

    Resume[] getAll();

    void update(Resume resume);

    void delete(String uuid);

    int size();

    void clear();
}
