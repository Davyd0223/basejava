package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws StorageException, IOException;

    protected abstract Resume doRead(InputStream is) throws StorageException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(String.valueOf(dir));
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is noe directory or is not writable");
        }
    }

    public Path getDirectory() {
        return directory;
    }

    @Override
    protected Path findIndex(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (Exception e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (Exception e) {
            throw new StorageException("Path read error", null, e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {
                throw new StorageException("Path does not exist", null);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to delete path", null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {

        if (directory == null) {
            throw new StorageException("Couldn't delete the Path", null);
        }

        List<Resume> resumes = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path item : stream) {
                if (Files.isRegularFile(item)) {
                    try (InputStream is = new BufferedInputStream(new FileInputStream(item.toFile()))) {
                        resumes.add(doRead(is));
                    } catch (FileNotFoundException e) {
                        throw new StorageException("Error reading file", null, e);
                    }
                }
            }
        } catch (IOException e) {
            throw new StorageException("Error accessing directory", null, e);
        }
        return resumes;
    }

    @Override
    public int size() {
        return directory.getNameCount();
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }
}
