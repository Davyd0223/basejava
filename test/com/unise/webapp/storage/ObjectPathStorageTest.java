package com.unise.webapp.storage;

import com.unise.webapp.storage.serializer.ObjectStreamSerializer;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}