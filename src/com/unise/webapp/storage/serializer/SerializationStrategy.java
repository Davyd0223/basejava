package com.unise.webapp.storage.serializer;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    void doWrite(Resume r, OutputStream os) throws StorageException;

    Resume doRead(InputStream is) throws StorageException;
}
