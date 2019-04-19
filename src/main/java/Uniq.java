import java.io.*;
import java.util.*;

public class Uniq {

    private static String inputFile;
    private String outputFile;
    private Integer numberFirstSym;
    private boolean ignoreCase;
    private boolean deletedNum;
    private boolean onlyUnique;

    public Uniq (String inputFile, String outputFile, boolean ignoreCase, Integer numberFirstSym,
                 boolean deletedNum, boolean onlyUnique) {
        Uniq.inputFile = inputFile;
        this.outputFile = outputFile;
        this.numberFirstSym = numberFirstSym;
        this.ignoreCase = ignoreCase;
        this.deletedNum = deletedNum;
        this.onlyUnique = onlyUnique;
    }

    public static List<String> read (InputStream input) throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        if (lines.isEmpty()) throw new IOException("No input data");
        return lines;
    }

    public String uniqueLines(List<String> inputList) {
        List<Map.Entry<Integer, String>> result = new ArrayList<>();

        List<String> input = new ArrayList<>();

        if (ignoreCase) {
            for (String line: inputList)
                input.add(line.toLowerCase());
        }
        else input = inputList;

        String subRepeated = input.get(0).substring(numberFirstSym);
        String repeated = input.get(0);
        int count = 0;
        for (int i = 1; i < input.size(); i++) {
            String current = input.get(i).substring(numberFirstSym);
                if (subRepeated.equals(current)) {
                    count++;
                }
                else {
                    result.add(new AbstractMap.SimpleEntry<>(count + 1, repeated));
                    subRepeated = current;
                    count = 0;
                    repeated = input.get(i);
                }
        }
        if (count == 0) {
            result.add(new AbstractMap.SimpleEntry<>(count + 1, repeated));
        }

        //Формирование результата
        StringBuilder resultStr = new StringBuilder();
        if (deletedNum) {
            for (Map.Entry line: result) {
                resultStr.append(line.getKey().toString()).append(" ").append(line.getValue()).append("\r\n");
            }
        }
        else {
            if(!onlyUnique) {
                for (Map.Entry line : result) {
                    resultStr.append(line.getValue()).append("\r\n");
                }
            }
            else {
                for (Map.Entry line : result) {
                    if (line.getKey().equals(1)) {
                        resultStr.append(line.getValue()).append("\r\n");
                    }
                }
            }
        }
        return resultStr.toString();
    }

    public void write (String outputFile, String output) throws IOException {
        if (outputFile != null) {
            File out = new File(outputFile);
            if (!out.exists()) throw new IOException("Output file does not exist");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(output);
            writer.close();
        }
        else System.out.print(output);
    }

    public void result() throws IOException {
        String result;
        if (numberFirstSym < 0) throw new IOException("This parameter cannot be a negative number");

        List<String> inputList;
        if (inputFile != null) {
            File in = new File(inputFile);
            if (!in.exists()) throw new IOException("Input file does not exist");
            inputList = read(new FileInputStream(inputFile));
        }
        else inputList = read(System.in);

        result = uniqueLines(inputList);
        write(outputFile, result);
    }
}