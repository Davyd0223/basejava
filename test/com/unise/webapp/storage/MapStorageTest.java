package com.unise.webapp.storage;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest(){
        super(new MapUuidStorage());
    }
}
