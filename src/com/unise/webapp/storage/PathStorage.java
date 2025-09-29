package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import com.unise.webapp.storage.serializer.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    protected PathStorage(String dir, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(dir, "directory must not be null");

        directory = Paths.get(dir);
        this.serializationStrategy = serializationStrategy;
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
                throw new StorageException("Path does not exist", (Exception) null);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to delete path", null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();

        if (directory == null) {
            throw new StorageException("Couldn't delete the Path", (Exception) null);
        }

        path().filter(Files::isRegularFile).
                map(item -> {
                    try {
                        return resumes.add(serializationStrategy.doRead((InputStream) item));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        return resumes;
    }

    @Override
    public int size() {
        return (int) path().count();
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
