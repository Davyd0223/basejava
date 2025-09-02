package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractFileStorage implements SerializationStrategy {

    protected ObjectStreamPathStorage(Path directory) throws IllegalAccessException {
        super(directory.toFile());
        setSerializationStrategy(this);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws StorageException, IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws StorageException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            Object result = ois.readObject();
            if (result instanceof Resume) {
                return (Resume) result;
            } else {
                throw new StorageException("its not object Resume", null);
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("Error reading resume", null, e);
        }
    }
}
