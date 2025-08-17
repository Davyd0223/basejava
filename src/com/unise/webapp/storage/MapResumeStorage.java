package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<String> {
    private final Map<String, Resume> mapResume = new HashMap<>();

    @Override
    protected String findIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return mapResume.containsKey(searchKey);
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return mapResume.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(String searchKey) {
        mapResume.remove(searchKey);
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
