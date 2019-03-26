package pl.edu.wszib.springfirststeps;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOFileJsonReader implements FileJsonReader {

    @Override
    public String readJson(String filename) {
        Path path = Paths.get(filename);
        return readFile(path);
    }

    private String readFile(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
