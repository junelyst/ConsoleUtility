import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UniqTest {

    private ClassLoader classLoader = getClass().getClassLoader();
    private String file1 = new File(classLoader.getResource("file1.txt").getFile()).getPath();
    private String file1res1 = new File(classLoader.getResource("file1res1.txt").getFile()).getPath();
    private String file1res2 = new File(classLoader.getResource("file1res2.txt").getFile()).getPath();
    private String file1res1Exp = new File(classLoader.getResource("file1res1Expected.txt").getFile()).getPath();
    private String file1res2Exp = new File(classLoader.getResource("file1res2Expected.txt").getFile()).getPath();
    private String file2 = new File(classLoader.getResource("file2.txt").getFile()).getPath();
    private String file2res1 = new File(classLoader.getResource("file2res1.txt").getFile()).getPath();
    private String file2res1Exp = new File(classLoader.getResource("file2res1Expected.txt").getFile()).getPath();
    private String consoleOutput = new File(classLoader.getResource("consoleOutput.txt").getFile()).getPath();
    private String consoleOutputExp = new File(classLoader.getResource("consoleOutput.txt").getFile()).getPath();
    private String empty = new File(classLoader.getResource("empty.txt").getFile()).getPath();

    public boolean fileComparison (String expect, String real) throws IOException {
        List<String> expectedList = Uniq.read(new FileInputStream(expect));
        List<String> realList = Uniq.read(new FileInputStream(real));
        return expectedList.equals(realList);
    }

    @Test
    public void fromFileToFile() throws IOException {
        Uniq uniq1 = new Uniq(file1, file1res1,
                false, 0, false, false);
        uniq1.result();
        assertTrue(fileComparison(file1res1, file1res1Exp));

        Uniq uniq2 = new Uniq(file1, file1res2, false, 0, true, false);
        uniq2.result();
        assertTrue(fileComparison(file1res2Exp, file1res2));
    }

    @Test
    public void fromFileToConsole() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Uniq uniq = new Uniq(file1, null,
                true, 0, false, true);
        uniq.result();

        String consoleOut = output.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(consoleOutput));
        writer.write(consoleOut);
        writer.close();
        assertTrue(fileComparison(consoleOutputExp, consoleOutput));
    }

    @Test
    public void fromConsoleToFile() throws IOException {

        System.setIn(new FileInputStream(file1));
        Uniq uniq = new Uniq(null, file1res1,
                false, 0, false, false);
        uniq.result();
        assertTrue(fileComparison(file1res1Exp, file1res1));
        System.setIn(System.in);
    }

    @Test
    public void numFirstSym() throws IOException {
        Uniq uniq1 = new Uniq(file2, file2res1,
                false, 6,true, false);
        uniq1.result();
        assertTrue(fileComparison(file2res1Exp, file2res1));

        final Uniq uniq2 = new Uniq(file2, file2res1,
                false, -6, true, false);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                uniq2.result();
            }
        });
    }

    @Test
    public void notExist() {
        final Uniq uniq1 = new Uniq("src\\test\\java\\file3", null,
                false, 0, false, false);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                uniq1.result();
            }
        });

        final Uniq uniq2 = new Uniq(file1, "src\\test\\java\\file3",
                false, 0, false, false);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                uniq2.result();
            }
        });
    }

    @Test
    public void emptyFile() {
        final Uniq uniq = new Uniq(empty, null,
                false, 0, true, false);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                uniq.result();
            }
        });
    }
}