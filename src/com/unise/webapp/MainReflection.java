package com.unise.webapp;

import com.unise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = new Resume("Name");
        Class<?> field = resume.getClass();
        Method toString = field.getMethod("toString");
        Object result = toString.invoke(resume);
        System.out.println(result);
    }
}
