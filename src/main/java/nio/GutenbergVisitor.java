package nio;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Ovaj {@linkplain SimpleFileVisitor} obilazi poddirektorij s knjigama tipa "ime.txt", cita sve retke i zapisuje ih
 * u novu datoteku imena <i>gutenberg_books.txt</i>.
 *
 * @author darian
 * @version 1.0
 */
public class GutenbergVisitor extends SimpleFileVisitor<Path> {
    private static final Path GUTENBERG_BOOKS_PATH = Paths.get("./gutenberg_books.txt");
    /**
     * Pocetni direktorij obilaska
     */
    private Path startDir;
    /**
     * Broj procitanih knjiga tijekom obilaska
     */
    private int noBooksRead = 0;
    /**
     * Broj procitanih redaka tijekom obilaska
     */
    private int noLinesRead = 0;
    private List<String> lines = new LinkedList<>();

    /**
     * Konstruktor.
     *
     * @param startDir pocetni direktorij obilaska
     */
    public GutenbergVisitor(Path startDir) {
        this.startDir = Objects.requireNonNull(startDir);
    }

    public List<String> getLines() {
        return lines;
    }

    /**
     * @return {@link #startDir}
     */
    public Path getStartDir() {
        return startDir;
    }

    /**
     * @return {@link #noBooksRead}
     */
    public int getNoBooksRead() {
        return noBooksRead;
    }

    /**
     * @return {@link #noLinesRead}
     */
    public int getNoLinesRead() {
        return noLinesRead;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //todo: implementacija
        if (dir.equals(startDir)) {
            System.out.println(String.format("Zapocinjem obradu direktorija: %s", dir));
        } else {
            System.out.println(String.format("Obradujem direktorij %s, ", dir));
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(String.format("Obradujem datoteku %s", file));
        lines.addAll(Files.readAllLines(file, StandardCharsets.ISO_8859_1));
        noBooksRead++;
        noLinesRead += lines.size();

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println(String.format("Obraden direktorij %s", dir));

        if (dir.equals(startDir)) {
            System.out.println("Broj procitanih knjiga: " + noBooksRead + "\nBroj ucitanih redaka: " + noLinesRead);
            System.out.println(lines.size());
            try (BufferedWriter writer = Files.newBufferedWriter(GUTENBERG_BOOKS_PATH, StandardCharsets.UTF_8)) {
                for (String l : lines) {
                    if (!l.isEmpty()) {
                        writer.write(l);
                        writer.newLine();
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
