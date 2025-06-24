package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstracktArrayStorage {

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