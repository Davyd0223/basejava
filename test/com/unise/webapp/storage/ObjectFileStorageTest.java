package com.unise.webapp.storage;

import com.unise.webapp.storage.serializer.ObjectStreamSerializer;

class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() throws IllegalAccessException {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}