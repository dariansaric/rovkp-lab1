import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HadoopMain {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        LocalFileSystem localFileSystem = FileSystem.getLocal(configuration);
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:9000"), configuration);
        //FileSystem fileSystem = FileSystem.get(configuration);
        Path localPath = new Path("./gutenberg.zip");
        Path hdfsPath = new Path("./gutenberg.zip");

        System.out.println(localFileSystem.getFileStatus(localPath));
        System.out.println(fileSystem.getFileStatus(hdfsPath));
    }
}
