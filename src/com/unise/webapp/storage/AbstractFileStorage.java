package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

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

    protected abstract void doWrite(Resume r, File file);

    @Override
    protected Resume doGet(File file) {
        return null;
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
        directory.delete();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Collections.emptyList();
    }

    @Override
    public int size() {
        return (int) directory.length();
    }

    @Override
    public void clear() {
        directory.delete();
    }

    public File getDirectory() {
        return directory;
    }
}
