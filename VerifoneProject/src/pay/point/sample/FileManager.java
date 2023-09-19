
package pay.point.sample;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FileManager {

public void sendLineItem(String url, String key, String value) {

//        url = "https://example.com/api/endpoint";

  try {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Add request body
//        String requestBody = "param1=value1&param2=value2";
        String requestBody = key + "=" + value;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestBody);
        wr.flush();
        wr.close();

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print response
        System.out.println(response.toString());

    }catch(Exception e) {
        e.printStackTrace();

        
    }

    }



    public void compareFile(String filepath1, String filepath2)
    {

//        File file1 = new File("path/to/file1");
  //      File file2 = new File("path/to/file2");
        File file1 = new File(filepath1);
        File file2 = new File(filepath2);

        if (!file1.exists() || !file2.exists()) {
            System.out.println("One or both files do not exist.");
            return;
        }
        if (!file1.isFile() || !file2.isFile()) {
            System.out.println("One or both paths do not point to a file.");
            return;
        }
        if (file1.length() != file2.length()) {
            System.out.println("Files are not the same size.");
            return;
        }
        try (InputStream inputStream1 = new FileInputStream(file1);
             InputStream inputStream2 = new FileInputStream(file2)) {
            int byte1, byte2;
            long byteCount = 0;
            while ((byte1 = inputStream1.read()) != -1) {
                byte2 = inputStream2.read();
                if (byte1 != byte2) {
                    System.out.printf("Files differ at byte %d: %02X != %02X%n", byteCount, byte1, byte2);
                    return;
                }
                byteCount++;
            }
            if (byteCount != file1.length()) {
                System.out.println("Byte count does not match file size.");
                return;
            }
            System.out.println("Files are identical.");
            System.out.printf("File 1 last modified: %tc%n", new Date(file1.lastModified()));
            System.out.printf("File 2 last modified: %tc%n", new Date(file2.lastModified()));
            System.out.printf("File 1 permissions: %s%n", file1.canRead() ? "r" : "-");
            System.out.printf("                     %s%n", file1.canWrite() ? "w" : "-");
            System.out.printf("                     %s%n", file1.canExecute() ? "x" : "-");
            System.out.printf("File 2 permissions: %s%n", file2.canRead() ? "r" : "-");
            System.out.printf("                     %s%n", file2.canWrite() ? "w" : "-");
            System.out.printf("                     %s%n", file2.canExecute() ? "x" : "-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



public void uploadFile(String filename){

    try {
        File file = new File("barcodes_only.csv");
        String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary string
        String url = "https://lockwind.com/inbox/lockwind/index.php";
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        OutputStream output = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
        writer.append("--" + boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append("\r\n");
        writer.append("Content-Type: text/plain; charset=UTF-8").append("\r\n");
        writer.append("\r\n");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            writer.append(line).append("\r\n");
        }
        reader.close();
        writer.flush();
        writer.append("--" + boundary + "--").append("\r\n");
        writer.close();
        output.close();
        InputStream input = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
        }
        
        in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void downloadFile(String filename, String destinationDirectory){
//        String fileUrl = "https://lockwind.com/inbox/lockwind/index.php";
  //      String saveDir = "path/to/save/directory";

        String fileUrl = filename;
        String saveDir = destinationDirectory;

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = connection.getHeaderField("Content-Disposition");
                String contentType = connection.getContentType();
                int contentLength = connection.getContentLength();
                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
                }
                System.out.println("Downloading file: " + fileName);
                InputStream inputStream = connection.getInputStream();
                String saveFilePath = saveDir + File.separator + fileName;
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                System.out.println("File downloaded successfully.");
            } else {
                System.out.println("File not found on the server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public void fileChecker(String filename) {
        try {
//            URL url = new URL("http://example.com/file.txt");
            
            URL url = new URL(filename);

            // URL url = new URL("https://lockwind.com/test/index.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("File exists on website.");
            } else {
                System.out.println("File does not exist on website.");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

public void writeFile(String filename, String line){

}




    public static void main(String[] args) {

        FileManager test = new FileManager();

        // test.fileChecker("https://lockwind.com/test/index.php");


        String url = "https://lockwind.com/test/pim_index.php";
        String key = "reference_code";
        String value = "075720431151";

        test.sendLineItem(url,key, value);
    
        
    }
}
