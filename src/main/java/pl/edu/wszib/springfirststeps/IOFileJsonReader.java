package pl.edu.wszib.springfirststeps;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;

public class IOFileJsonReader implements FileJsonReader {

    @Override
    public String readJson(String path) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = fileReader.readLine();
            while (line != null) {
                sb.append(line);
                line = fileReader.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
