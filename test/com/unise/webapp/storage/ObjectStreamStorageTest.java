package com.unise.webapp.storage;

public class ObjectStreamStorageTest extends  AbstractStorageTest {
    public ObjectStreamStorageTest() throws IllegalAccessException {
            super(new ObjectStreamStorage(STORAGE_DIR));

    }
}
