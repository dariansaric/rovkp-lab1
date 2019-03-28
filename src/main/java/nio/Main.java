package nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    //todo: popravak glupog errora da bi mogao odgovoriti na pitanja
    private static final Path START_PATH = Paths.get("./gutenberg");

    public static void main(String[] args) throws IOException {
        System.out.println("Zapocinjem obradu direktorija gutenberg");

        Files.walkFileTree(START_PATH, new GutenbergVisitor(START_PATH));
    }
}
