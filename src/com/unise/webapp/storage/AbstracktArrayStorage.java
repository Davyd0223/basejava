package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstracktArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume r) {
        int index = findIndex(r.getUuid());

        if (size >= storage.length) {
            System.out.println("Массив заполнен, не удалось добавить резюме: " + r.getUuid());
        } else if (index != -1) {
            System.out.println("Резюме c UUID " + r.getUuid() + " уже существует");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];

        } else {
            System.out.println("Резюме не существует в storage");
        }
        return null;
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
