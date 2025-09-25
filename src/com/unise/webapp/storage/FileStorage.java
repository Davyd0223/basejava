package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import com.unise.webapp.storage.serializer.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final SerializationStrategy serializationStrategy;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) throws IllegalAccessException {
        Objects.requireNonNull(directory, "directory must not be null");

        this.serializationStrategy = serializationStrategy;
        if (!directory.isDirectory()) {
            throw new IllegalAccessException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canWrite() || !directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

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
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (Exception e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("File read error", null, e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Couldn't delete the file", (Exception) null);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {

        File[] files = list();
        List<Resume> resume = new ArrayList<>(files.length);

        for (File item : files) {
            resume.add(doGet(item));
        }
        return resume;
    }

    @Override
    public int size() {
        return list().length;
    }

    @Override
    public void clear() {
        File[] files = list();
        for (File item : files) {
            if (item.isFile()) {
                doDelete(item);
            }
        }
    }

    private File[] list() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Directory read error", (Exception) null);
        }
        return list;
    }
}
