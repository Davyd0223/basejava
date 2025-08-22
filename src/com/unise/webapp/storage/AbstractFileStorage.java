package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) throws IllegalAccessException {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalAccessException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canWrite() || directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, File file) throws StorageException;

    protected abstract Resume doRead(File file) throws StorageException;

    public File getDirectory() {
        return directory;
    }

    @Override
    protected File findIndex(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (Exception e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Couldn't delete the file", null);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if(files == null){
            throw new StorageException("Couldn't delete the file", null);
        }
        List<Resume> resume = new ArrayList<>();
        for(File item : files){
            resume.add(doRead(item));
        }
        return resume;
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        int countFile = 0;
        for (String item : list) {
            File file = new File(directory, item);
            if (file.isFile()) {
                countFile++;
            }
        }
        return countFile;
    }

    @Override
    public void clear() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        for (String item : list) {
            File file = new File(directory, item);
            if (file.isFile()) {
                doDelete(file);
            }
        }
    }
}
