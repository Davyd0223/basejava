import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int count = 0;
    int size = 0;

    void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    void save(Resume r) {
        if (count < storage.length) {
            storage[count] = r;
            count++;
            size++;
        } else {
            System.out.println("Массив заполнен, не удалось добавить резюме: " + r);
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if(storage[i].uuid.equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                if(i < size - 1){
                    storage[i] = storage[size - 1];
                }
                storage[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
            Resume[] resumeAll = new Resume[size];
            for (int i = 0; i < size; i++) {
                    resumeAll[i] = storage[i];
            }
            return resumeAll;
    }

    int size() {
        return size;
    }
}