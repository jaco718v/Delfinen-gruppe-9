package database;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
    public ArrayList<String[]> readCSV(String filepath) {

        ArrayList<String[]> csvData = new ArrayList<>();
        try {
            FileReader fr = new FileReader(new File(filepath));
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArray;
            while ((line = br.readLine()) != null) {
                tempArray = line.split(",");
                csvData.add(tempArray);
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return csvData;
    }

    public void writeCSV(String filepath, ArrayList<String[]> data) throws IOException {
        ArrayList<String[]> csvData = new ArrayList<String[]>();
        try {
            FileReader fr = new FileReader(new File(filepath));
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArray;
            while ((line = br.readLine()) != null) {
                tempArray = line.split(",");
                csvData.add(tempArray);
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        csvData.addAll(data);
        try (PrintWriter pw = new PrintWriter(new File(filepath))) {
            csvData.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
