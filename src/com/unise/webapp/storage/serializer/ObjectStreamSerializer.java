package com.unise.webapp.storage.serializer;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws StorageException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        } catch (IOException e) {
            throw new StorageException("Error write resume", null, e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws StorageException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
