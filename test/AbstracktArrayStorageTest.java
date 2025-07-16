import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import com.unise.webapp.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstracktArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstracktArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(RESUME_2);
        });
        Assertions.assertThrows(ExistStorageException.class, () -> {
            int size = storage.size();
            if (5 > size) {
                throw new ExistStorageException("uuid5");
            }
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
        storage.getAll();
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assertions.assertSame(newResume, storage.get(UUID_1));
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