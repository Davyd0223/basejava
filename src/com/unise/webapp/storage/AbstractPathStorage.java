package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    SerializationStrategy serializationStrategy;

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

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
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (Exception e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
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
        List<Resume> resumes = new ArrayList<>();

        if (directory == null) {
            throw new StorageException("Couldn't delete the Path", null);
        }

        path().filter(Files::isRegularFile).
                map(item -> resumes.add(serializationStrategy.doRead((InputStream) item)));

        return resumes;
    }

    @Override
    public int size() {
        return (int) path().count(); //(int) stream.count();
    }

    @Override
    public void clear() {
        path().forEach(this::doDelete);
    }

    private Stream<Path> path() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error reading file", null, e);
        }
    }
}
