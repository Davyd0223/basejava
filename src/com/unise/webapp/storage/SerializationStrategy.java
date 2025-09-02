package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy  {
     void doWrite(Resume r, OutputStream os) throws StorageException, IOException;

    Resume doRead(InputStream is) throws StorageException;
}
