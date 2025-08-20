package com.unise.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String startPath = "C:\\Projects\\basejava";

      try(Stream<Path> stream = Files.walk(Paths.get(startPath))){
          List<Path> pathList = stream.toList();
          for (Path path : pathList) {
              if(Files.isRegularFile(path)){
                  System.out.println(path);
              }
          }
      }
    }
}
