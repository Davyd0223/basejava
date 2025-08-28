package com.unise.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    public ObjectStreamPathStorageTest() throws IllegalAccessException {
        super(new ObjectStreamPathStorage(STORAGE_DIR.toPath()));
    }
}
