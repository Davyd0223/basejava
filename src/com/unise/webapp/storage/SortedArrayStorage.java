package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstracktArrayStorage {

    protected final Resume[] storageSort = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {

    }

    public void save(Resume r) {
        int index = Arrays.binarySearch(storageSort, 0, size, Comparator.comparing(Resume::getUuid));

        if (index >= 0) {
            System.out.println("Резюме c UUID " + r.getUuid() + " уже существует");
        } else {
            int insertionPoint = -(index + 1);
            if(size >= storageSort.length){
                System.out.println("Массив заполнен");
            }
            System.arraycopy(storageSort, insertionPoint,storageSort,insertionPoint +1, size - insertionPoint);
            storageSort[insertionPoint] = r;
            size++;
        }
    }

    public void delete(String uuid) {

    }

    public void update(Resume resume) {

    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    protected int findIndex(String uuid) {
        int indexUuid = -1;
        Arrays.sort(storageSort);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                indexUuid = i;
            }
        }
        //int i = Arrays.binarySearch(storageSort, 0, size);
        return indexUuid;
    }

}
