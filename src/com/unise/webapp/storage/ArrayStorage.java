package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) {
            storage[size] = r;
            size++;
        } else if (!r.getUuid().equals(storage[size].getUuid())) {
            System.out.printf("Резюме %s отсутсвтует", r.getUuid());
        } else if (size > storage.length) {
            System.out.println("Массив заполнен, не удалось добавить резюме: " + r.getUuid());
        }
    }

    public void check(String uuid){
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    System.out.println("Резюме " +  storage[i].getUuid() + " существует в storage");
                }
            }
    }

    public Resume get(String uuid) {
        check(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        check(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    public void update(Resume resume) {
        check(resume.getUuid());
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int i = 0; i < size; i++) {
            allResume[i] = storage[i];
        }
        return allResume;
    }

    public int size() {
        return size;
    }
}