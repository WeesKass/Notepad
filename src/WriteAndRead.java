import java.io.*;

/*
    The main purpose of The WriteAndRead class is to read a file line by line and store it to the String
 */

public class WriteAndRead {

    public static String readFromFile(String fileName) {
        String lines = "";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines = lines + line + "\n";
            }

            bufferedReader.close();
        } catch (IOException ioe) {
            System.out.println(ioe);

        }

        return lines;
    }

    public static void writeToFile(String filename, String lines) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(lines);
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
