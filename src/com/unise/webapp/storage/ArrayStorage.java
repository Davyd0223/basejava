package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstracktArrayStorage {

    @Override
    protected void insertResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }

    protected int findIndex(String uuid) {
        int indexUuid = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                indexUuid = i;
            }
        }
        return indexUuid;
    }
}