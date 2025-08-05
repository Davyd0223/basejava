package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> mapResume = new HashMap<>();

    @Override
    protected Object findIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapResume.containsKey(searchKey.toString());
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapResume.put(r.getUuid(),r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapResume.get(searchKey.toString());
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapResume.put(r.getUuid(),r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapResume.remove(searchKey.toString());
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Collections.emptyList();
    }

    @Override
    public int size() {
        return mapResume.size();
    }

    @Override
    public void clear() {
        mapResume.clear();
    }
}
