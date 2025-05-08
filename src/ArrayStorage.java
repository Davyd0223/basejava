import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int count = 0;

    void clear() {
        for (int i = 0; i < storage.length - 1; i++) {
            delete(String.valueOf(storage[i]));
        }
    }

    void save(Resume r) {
        if (count < storage.length) {
            storage[count] = r;
            count++;
        } else {
            System.out.println("Массив заполнен, не удалось добавить резюме: " + r);
        }
    }

    Resume get(String uuid) {
        Resume result = null;
        for (Resume str : storage) {
            if (str != null && str.uuid.equals(uuid)) {
                result = str;
            }
        }
        return result;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = null;
            }
        }
        for (int i = 1; i < storage.length - 1; i++) {
            storage[i - 1] = storage[i];
            storage[i] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[10000];
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null) {
                result[i] = storage[i];
            }
        }
        return result;
    }

    int size() {
        int result = 0;
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null) {
                result++;
            }
        }
        return result;
    }
}
