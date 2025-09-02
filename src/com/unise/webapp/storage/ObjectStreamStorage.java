package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage implements SerializationStrategy  {

    protected ObjectStreamStorage(File directory) throws IllegalAccessException {
        super(directory);
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
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Error rear resume", null, e);
        }
    }
}
