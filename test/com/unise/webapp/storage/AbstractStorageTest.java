package com.unise.webapp.storage;

import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1, "Name1"));
        storage.save(new Resume(UUID_2, "Name2"));
        storage.save(new Resume(UUID_3, "Name3"));
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    public void doSaveOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("Name" + i));
        }
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume("Overflow"));
        });
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);

        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    public void getAll() throws Exception {
        storage.getAllSorted();
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New name");
        storage.update(newResume);
        Assertions.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_1);
        });
    }

    private void assertGet(Resume r) {
        Assertions.assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}