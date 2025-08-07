package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertionPoint = -(index + 1);
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = resume;
    }

    @Override
    protected void removeResume(int index) {
        int elementMoved = size - index - 1;
        if (elementMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, elementMoved);
        }
    }

    protected Integer findIndex(String uuid) {
        Resume searchKey = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
