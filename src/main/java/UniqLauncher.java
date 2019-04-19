import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class UniqLauncher {
    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inputFile = null;

    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private String outputFile = null;

    @Option(name = "-i", usage = "Ignore case")
    private boolean ignoreCase;

    @Option(name = "-s", usage = "Ignore first N symbols")
    private int numberFirstSym = 0;

    @Option(name = "-u", usage = "Only unique strings")
    private boolean onlyUnique;

    @Option(name = "-c", usage = "Number of deleted strings")
    private boolean deletedNum;


    public static void main(String[] args) {
        new UniqLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar uniq.jar uniq [-i] [-u] [-c] [-s num] [-o ofile] [file]");
            parser.printUsage(System.err);
            return;
        }
        try {
            Uniq uniq = new Uniq(inputFile, outputFile, ignoreCase, numberFirstSym,
            deletedNum, onlyUnique);
            uniq.result();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}