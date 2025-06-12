package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] STORAGE_LIMIT = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(STORAGE_LIMIT, null);
        size = 0;
    }

    public void save(Resume r) {
        int findIndex = 0;
        for (int i = 0; i < size; i++) {
            if (r.getUuid().equals(STORAGE_LIMIT[i].getUuid())) {
                findIndex = i;
            }
        }

        if (size > STORAGE_LIMIT.length) {
            System.out.println("Массив заполнен, не удалось добавить резюме: " + r.getUuid());
        } else if (STORAGE_LIMIT[findIndex] != null && r.getUuid().equals(STORAGE_LIMIT[findIndex].getUuid())) {
            System.out.println("Резюме существует в storage");
        } else if (size < STORAGE_LIMIT.length) {
            STORAGE_LIMIT[size] = r;
            size++;
        }

    }

    public int findIndex(String uuid) {
        int indexUuid = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(STORAGE_LIMIT[i].getUuid())) {
                indexUuid = i;
            }
        }
        return indexUuid;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (STORAGE_LIMIT[index].getUuid().equals(uuid)) {
                return STORAGE_LIMIT[index];
            }
        } else {
            System.out.println("Резюме не существует в storage");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (STORAGE_LIMIT[index].getUuid().equals(uuid)) {
                STORAGE_LIMIT[index] = STORAGE_LIMIT[size - 1];
                STORAGE_LIMIT[size - 1] = null;
                size--;
            }
        } else {
            System.out.println("Резюме не существует в storage");
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            if (resume.getUuid().equals(STORAGE_LIMIT[index].getUuid())) {
                STORAGE_LIMIT[index] = resume;
            }
        } else {
            System.out.println("Резюме не существует в storage");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE_LIMIT, size);
    }

    public int size() {
        return size;
    }
}