package com.mmg.neo4j;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
public class CSVUtil {
    public static void main(String[] args) throws IOException {
        String readFile = "C:/software/neo4j-community-4.3.3/import/relation.csv";
        String writeFile = "C:/software/neo4j-community-4.3.3/import/relation1.csv";
        CsvReader csvReader = new CsvReader(readFile, ',', StandardCharsets.UTF_8);
        CsvWriter csvWriter = new CsvWriter(writeFile, ',', StandardCharsets.UTF_8);
        while (csvReader.readRecord()) {
            String s = csvReader.get(2);
            if (Integer.parseInt(s) > 4) {
                csvWriter.writeRecord(csvReader.getValues());
            }
        }
        csvReader.close();
        csvWriter.close();
    }
}
