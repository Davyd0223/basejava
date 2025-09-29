package com.unise.webapp.storage;

import com.unise.webapp.Config;

import java.io.IOException;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() throws IOException {
        super(Config.get().getStorage());
    }
}
