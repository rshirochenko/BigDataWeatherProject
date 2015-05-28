import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.io.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



public class Main {

    public static void main(String[] args) {


        String server = "ftp.ncdc.noaa.gov";
        int port = 21;
        String user = "anonymous";
        String pass = "john.maslov@gmail.com";

        FTPClient ftpClient = new FTPClient();

        try {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HDFSClient hdfs= new HDFSClient();


        InputStream inputStream = null;
        try {
            inputStream= new FileInputStream("stations.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner sc  = new Scanner(inputStream, "UTF-8");
        Stack<String> stationNameStrings = new Stack<String>();

        while (sc.hasNextLine()) {
            stationNameStrings.push(sc.nextLine().split(";")[0]);

        }

        String[] downloadedStationNames = new String[stationNameStrings.size()];

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i=0; i<stationNameStrings.size();i++){
            downloadedStationNames[i]= stationNameStrings.pop();
            String remoteFile = "/pub/data/noaa/" + year + "/" + downloadedStationNames[i] +"-"+ year + ".gz";
            File downloadFile = new File(downloadedStationNames[i] +"-"+ year + ".gz");
            OutputStream outputStream = null;
            boolean success= false;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                success = ftpClient.retrieveFile(remoteFile, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (success) {
                System.out.println("File #" + downloadedStationNames[i] + " has been downloaded successfully.");
                downloadedStationNames[i]+=";"+0;
            }else {
                System.out.println("File #" + downloadedStationNames[i] + " has been failed.");
                downloadedStationNames[i]+=";"+1;
            }


        }

        for (int i = 0; i < downloadedStationNames.length; i++) {

            if(downloadedStationNames[i].split(";")[1]=="0"){
            gzip.main(downloadedStationNames[i].split(";")[0] +"-"+ year + ".gz",downloadedStationNames[i].split(";")[0]+"-"+ year);
                try {
                    Files.deleteIfExists(Paths.get(downloadedStationNames[i].split(";")[0] +"-"+ year + ".gz"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ishJava.main(new String[]{downloadedStationNames[i].split(";")[0]+"-"+ year, downloadedStationNames[i].split(";")[0]+"-"+ year+".out"});
                try {
                    Files.deleteIfExists(Paths.get(downloadedStationNames[i].split(";")[0]+"-"+ year) );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean success = format(downloadedStationNames[i].split(";")[0]+"-"+ year+".out", downloadedStationNames[i].split(";")[0]+"-"+ year+"_final.out", "/", "");
                if(success){
                    try {
                        Files.deleteIfExists(Paths.get(downloadedStationNames[i].split(";")[0]+"-"+ year+".out"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {downloadedStationNames[i]=downloadedStationNames[i].split(";")[0]+";";}


                try {
                    hdfs.copyToLocal("/projects/bravvfings/dataset/ISD/"+downloadedStationNames[i].split(";")[0]+ "/"+downloadedStationNames[i].split(";")[0]+"_final.out", "downloadedStationNames[i].split(\";\")[0]+\"-\"+ year+\"_old.out");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<String> lines = new ArrayList<String>();

                try {
                    Scanner tempSc = new Scanner(new File("downloadedStationNames[i].split(\";\")[0]+\"-\"+ year+\"_old.out"));

                    while (sc.hasNextLine()) {
                        lines.add(sc.nextLine());
                    }

                    tempSc = new Scanner(new File(downloadedStationNames[i].split(";")[0]+"-"+ year+"_final.out"));
                    while (sc.hasNextLine()) {
                        lines.add(sc.nextLine());
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                String[] arr = lines.toArray(new String[0]);

                String[] arr2 = removeDuplicates(arr);

                Arrays.sort(arr2);

                File out = new File(downloadedStationNames[i].split(";")[0]+"-"+ year+"_final_updated.out");

                for(int j=0; j<arr2.length; j++){
                    try {
                        FileUtils.writeStringToFile(out, arr2[j] + "\n", true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }
        }






    }


    public static String[] removeDuplicates(String[] arr) {
        return new HashSet<String>(Arrays.asList(arr)).toArray(new String[0]);
    }


    public static boolean format(String input, String output, String dirname, String homedir){
        FileInputStream inputStream = null;
        Scanner sc = null;

        try {
            inputStream = new FileInputStream(input);
            sc = new Scanner(inputStream, "UTF-8");
            File out = new File(output);
            int tempDate = 0;
            float tempPressure = 0;
            int counter = 0;
            Queue<String> qe=new LinkedList<String>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] strArr = line.split(" ");

                //strArr[1].toCharArray();
                String temph = "";
                char[] chars=  strArr[1].toCharArray();
                for(int i=8; i<12; i++){
                    temph += chars[i];
                }
                // Long tempDateLocal = (Long.parseLong(strArr[1]) -600)  ;


                if (Integer.parseInt(temph)==600){
                    String tempString = dirname+" ";

                    for (int i = 1; i<strArr.length; i++){
                        tempString += strArr[i].replace(",",".")+" ";
                    }
                    qe.add(tempString.replace("  "," "));
                    //FileUtils.writeStringToFile(out, tempString + "\n", true);
                }


                // System.out.println(line);

            }
            String temp_old = null;
            String temp = null;
            while(!qe.isEmpty()){
                temp = qe.poll();
                // System.out.println(temp.split(" ")[2].replaceAll(",", "."));
                if(Float.parseFloat(temp.split(" ")[2].replaceAll(",", "."))!=0){
                    FileUtils.writeStringToFile(out, temp + "\n", true);
                }else{
                    if(!qe.isEmpty()&& !qe.peek().split(" ")[2].isEmpty() && Float.parseFloat(temp.split(" ")[2].replaceAll(",", "."))==0&& temp_old !=null && Float.parseFloat(temp_old.split(" ")[2].replaceAll(",", "."))!= 0 && Float.parseFloat(qe.peek().split(" ")[2].replaceAll(",", "."))!=0 ){

                        float tempPressure1 = ((Float.parseFloat(temp_old.split(" ")[2].replaceAll(",", "."))+ Float.parseFloat(qe.peek().split(" ")[2].replaceAll(",", ".")))/2);
                        temp = temp.split(" ")[0]+ " "+ temp.split(" ")[1]+ " "+ tempPressure1+" "+ temp.split(" ")[3];
                        FileUtils.writeStringToFile(out, temp + "\n", true);

                    }
                }
                temp_old = temp;


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}