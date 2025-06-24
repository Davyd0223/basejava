package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstracktArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    public abstract void save(Resume r);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];

        } else {
            System.out.println("Резюме не существует в storage");
        }
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            if (resume.getUuid().equals(storage[index].getUuid())) {
                storage[index] = resume;
            }
        } else {
            System.out.println("Резюме не существует в storage");
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме не существует в storage");
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    protected abstract int findIndex(String uuid);

}
