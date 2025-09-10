package com.unise.webapp.storage;

import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Projects\\basejava\\storage");

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    Resume resume1 = ResumeTestData.createResume(UUID_1, "Name1");
    Resume resume2 = ResumeTestData.createResume(UUID_2, "Name2");
    Resume resume3 = ResumeTestData.createResume(UUID_3, "Name3");
    Resume resume4 = ResumeTestData.createResume(UUID_4, "Name4");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
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
        storage.save(resume4);
        assertGet(resume4);
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
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);

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