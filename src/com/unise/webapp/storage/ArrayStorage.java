package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstracktArrayStorage {

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
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