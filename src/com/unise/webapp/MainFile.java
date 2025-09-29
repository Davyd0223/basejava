package com.unise.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) {
        String startPath = "C:\\Projects\\basejava";
        printDirectory(Paths.get(startPath), 0);
    }

    private static void printDirectory(Path path, int indentLevel) {
        if (!Files.exists(path)) {
            System.out.println("Путь не существует: " + path);
        }

        String indent = " ".repeat(indentLevel * 4);
        if (Files.isDirectory(path)) {
            System.out.println(indent + "Directory: " + path.getFileName());
            try (var stream = Files.list(path)) {
                for (Path p : stream.toList()) {
                    printDirectory(p, indentLevel + 1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(indent + "File: " + path.getFileName());
        }
    }
}
