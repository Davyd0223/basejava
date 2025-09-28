package com.unise.webapp;

import com.unise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }
}
