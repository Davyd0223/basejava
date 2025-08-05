package com.unise.webapp.storage;

import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }
    @Test
    public void saveOverflow(){
        doSaveOverflow();
    }
}