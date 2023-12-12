import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class RestaurantMenuReadCSV {


    public String startEnd(String filename, int start, int end){

        String temp;

        temp = "";

//        String csvFile = "barcodes.csv";
        String csvFile = filename;
        String line;
        String csvSplitBy = ",";
//    private BufferedImage image;

FileManager test = new FileManager();

// test.fileChecker("https://lockwind.com/test/index.php");


String url = "https://lockwind.com/test/pim_index.php";
String key = "reference_code";
String value = "719852922322";




        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            FileWriter outputFile = new FileWriter("barcodes_only.csv",true);

            // read first ten lines of file
            for (int i = 0; i < end; i++) {
                
                if ((line = br.readLine()) != null) {
                
                    if(i >= start)
                    {
                        String[] data = line.split(csvSplitBy);
                    
                        System.out.println(data[0]);
                        outputFile.write(data[0]+"\n");
                        
                        test.sendLineItem(url,key, data[0]);
                        

//                        for (String val : data) {
  //                      System.out.print(i + " : " + val + " \n");
    //                }
                    //System.out.println();

                    }else {
                        String[] data = line.split(csvSplitBy);
//                        for (String val : data) {
//                            System.out.print(val + " ");
                        }
  //                      System.out.println();
    
                    }
    
                }
            
                outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return temp;

    }



    public void startEndDetailed(String filename, int start, int end){

        String csvFile = filename;
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            FileWriter outputFile = new FileWriter("barcodes_3001_4000.csv");
//FileWriter outputFile = new FileWriter(filename,true);
            // read first ten lines of file
            for (int i = 0; i < end; i++) {
                
                if ((line = br.readLine()) != null) {
                
                    if(i >= start) {                    
                            System.out.println(line);
                            outputFile.write( line+"\n");    
                    }                        
                    }
                }
                outputFile.close();
            }
                catch (IOException e) {}
                

                }
                
            
                












    public static void main(String[] args) {

        System.out.println();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println(dtf.format(now));  
        
        int start = 3001;
        int end = 4000;

        RestaurantMenuReadCSV test = new RestaurantMenuReadCSV();

        String filename = "";
//        String filename = "barcodes_only_2200_2500.csv";
  //      test.startEnd(filename,start,end);


        filename = "barcodes.csv";
        test.startEndDetailed(filename,start,end);

    }
}