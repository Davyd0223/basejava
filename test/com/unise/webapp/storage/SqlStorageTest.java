package com.unise.webapp.storage;

import com.unise.webapp.Config;

import java.io.IOException;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() throws IOException {
        super(new SqlStorage(Config.get().getDbUrl(),  Config.get().getDbUser(), Config.get().getDbPassword()));
    }

}
