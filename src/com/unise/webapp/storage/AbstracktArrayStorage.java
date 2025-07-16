package com.unise.webapp.storage;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstracktArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void save(Resume r) {
        int index = findIndex(r.getUuid());

        if (size >= storage.length) {
            throw new ExistStorageException(r.getUuid());
        } else if (index != -1) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            if (resume.getUuid().equals(storage[index].getUuid())) {
                storage[index] = resume;
            }
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);

        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);

    protected abstract int findIndex(String uuid);

}
