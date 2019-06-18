package JavaFinalProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {
		
		File file;
		FileWriter writer;
		Data<Float> temp;
		String outputFileName;
		Utils(String outputFileName)
		{
			file = new File(outputFileName);
			writer = null;
			this.outputFileName = outputFileName;
		}
		
		public void writeDataIntoFile(Stack<Data> dataRecord) {                
        
        try {
            writer = new FileWriter(file, false);
            while(!dataRecord.empty()) {
            	temp = dataRecord.pop();
                writer.write(temp.getFirstInput() + temp.getOperator()
                + temp.getSecondInput()+ "=" + temp.getResult() + "\n");
                writer.flush();
            }  
            System.out.println("DONE");
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
