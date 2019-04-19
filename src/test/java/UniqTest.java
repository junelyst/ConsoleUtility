import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UniqTest {
    private String path = "src\\test\\java\\";

    public boolean fileComparison (String expect, String real) throws IOException {
        List<String> expectedList = Uniq.read(expect);
        List<String> realList = Uniq.read(real);
        return expectedList.equals(realList);
    }

    @Test
    public void fromFileToFile() throws IOException {

        Uniq uniq1 = new Uniq(path + "file1.txt", path + "file1res1.txt",
                false, 0,false, false);
        uniq1.result();
        assertTrue(fileComparison(path + "file1res1.txt", path + "file1res1Expected.txt"));

        Uniq uniq2 = new Uniq(path + "file1.txt", path + "file1res2.txt",
                false, 0,true, false);
        uniq2.result();
        assertTrue(fileComparison(path + "file1res2.txt", path + "file1res2Expected.txt"));
    }

    @Test
    public  void fromFileToConsole() throws IOException {
        Uniq uniq = new Uniq(path + "file1.txt", null,
                true, 0,false, true);
        uniq.result();
    }

    @Test
    public  void fromConsoleToFile() throws IOException {
        System.setIn(new FileInputStream(path + "file1.txt"));
        Uniq uniq = new Uniq(null, path + "file1res1.txt",
                false, 0,false, false);
        uniq.result();
        assertTrue(fileComparison(path + "file1res1.txt", path + "file1res1Expected.txt"));
    }

    @Test
    public  void numFirstSym() throws IOException {
        Uniq uniq1 = new Uniq(path + "file2.txt", path + "file2res1.txt",
                false, 6,true, false);
        uniq1.result();
        assertTrue(fileComparison(path + "file2res1.txt", path + "file2res1Expected.txt"));

        final Uniq uniq2 = new Uniq(path + "file2.txt", path + "file2res1.txt",
                false, -6,true, false);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                uniq2.result();
            }
        });
    }
}