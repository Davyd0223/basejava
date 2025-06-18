package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public abstract class AbstracktArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
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

    protected abstract int findIndex(String uuid);

}
