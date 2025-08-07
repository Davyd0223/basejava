package com.unise.webapp.storage;

import org.junit.jupiter.api.Test;

public class ArrayStorageTest extends AbstractStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }
    @Test
    public void saveOverflow(){
       doSaveOverflow();
    }
}