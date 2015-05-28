import java.io.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
//import org.gzip;

public class Copy {


    public static void main(String[] args) {

	
	// connection to FTP
        FTPClient ftpClient = new FTPClient();
        String server = "ftp.ncdc.noaa.gov";
        int port = 21;
        String user = "anonymous";
        String pass = "ivan.maslov@epfl.ch";

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("USstations.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(inputStream, "UTF-8");
       // String[] stations;
        Lock lockStations = new ReentrantLock();
        lockStations.lock();
        Stack<String> stations = new Stack<String>();
        lockStations.unlock();
// reading every line in a file
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] strArr = line.split("\t");
           stations.push(strArr[0]+"-"+strArr[1]);
        }

        Lock lockYears = new ReentrantLock();
        lockYears.lock();
        String[] years = new String[30];
        for(Integer i=2015; i>1990; i-- ){

            years[2015-i]= i.toString();
        }
        lockYears.unlock();

            Count[] qwe = new Count[1];

          for (int i = 0; i < qwe.length; i++) {


               qwe[i] = new Count(years, lockYears, stations.pop().toString(), stations, lockStations);
              try {
                   // while (qwe[i].mythread.isAlive()) {
                        System.out.println("Thread"+ i+ "have been started" );
                        Thread.sleep(15);
                    //}
                } catch (InterruptedException e) {
                    System.out.println("Main thread interrupted");
                }
                System.out.println("Main thread run is over");

            }

			//loop over all stations
            while(!stations.isEmpty()){
            worker(years, stations.pop().toString(), ftpClient);
                System.out.println(stations.capacity());
}




	







    }



    public static boolean worker(String[] years, String stNumber, FTPClient ftpClient){

        boolean foldderCreated = new File(stNumber).mkdir();

    for (int i = 0; i < years.length; i++) {


        try
        {
 
 // getting all years files for every stations

            String remoteFile = "/pub/data/noaa/" + years[i] + "/" + stNumber + "-" + years[i] + ".gz";
            File downloadFile = new File(stNumber+"/"+stNumber + "-" + years[i] + ".gz");
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
            outputStream.close();

            if (!success) {
                break;
                  System.out.println("File #" + years[i] + " has been downloaded successfully.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File #" + years[i] +" "+ stNumber+ " has been failed.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File #" + years[i] +" "+ stNumber+ " has been failed.");

        }
    }

    try {
        ftpClient.abort();
    } catch (IOException e) {
        e.printStackTrace();
    }

	//loop for unziping all files
    for (int i = 0; i < years.length; i++) {


	
        boolean success =gzip.main(stNumber+"/"+ stNumber + "-" + years[i]  + ".gz", stNumber+"/"+stNumber + "-" + years[i] );
        if (success)
        {
            Path path = Paths.get(stNumber+"/"+ stNumber + "-" + years[i]  + ".gz") ;
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }else {System.out.println("File unpacking   #" + years[i] +" "+ stNumber+ " has been failed.");

        }

    }
    String temp;

    System.out.println("mythread run is over" );


    return true;
}


}


  class gzip
{

    public static boolean main( String fileIn, String fileout)
    {
        gzip gZip = new gzip();
        boolean success = gZip.gunzipIt(fileIn, fileout);
        return success;
    }

    /**
     * GunZip it
     */
    public boolean gunzipIt(String fileIn, String fileout){

        byte[] buffer = new byte[1024];

        try{

            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(fileIn));

            FileOutputStream out =   new FileOutputStream(fileout);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            System.out.println("Done");
            return true;

        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }
}





class Count implements Runnable
{
    Thread mythread ;
    FTPClient ftpClient = new FTPClient();
    String server = "ftp.ncdc.noaa.gov";
    int port = 21;
    String user = "anonymous";
    String pass = "ivan.maslov@epfl.ch";
    String[] years;
    String stNumber;
    Stack<String> stations;
    Lock lockStations;
    Lock lockYears;
    Count(String[] years, Lock lockYears, String stNumber, Stack<String> stations, Lock lockStations)
    {
        mythread = new Thread(this, "my runnable thread");
        System.out.println("my thread created" + mythread);
        this.stations = stations;
        this.lockStations =lockStations;
        this.lockYears = lockYears;

        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            this.lockYears.lock();
            this.years = years;
            this.lockYears.unlock();

            this.stNumber = stNumber;
            System.out.println("Connection established");
            boolean foldderCreated = new File(stNumber).mkdir();
           // System.out.println(foldderCreated);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mythread.start();
    }

    public boolean fileExists(String file) throws IOException
    {
        return (ftpClient.listFiles(file).length > 0);
    }

    public void run()
    {

        for (int i = 0; i < years.length; i++) {


        try
        {


                String remoteFile = "/pub/data/noaa/" + years[i] + "/" + stNumber + "-" + years[i] + ".gz";
                File downloadFile = new File(stNumber+"/"+stNumber + "-" + years[i] + ".gz");
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
                outputStream.close();

                if (success) {
                    System.out.println("File #" + years[i] + " has been downloaded successfully.");
                }

            } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File #" + years[i] +" "+ stNumber+ " has been failed.");
            String temp;
            lockStations.lock();
            temp = stations.pop().toString();
            lockStations.unlock();
            new Count(years, lockYears, temp, stations, lockStations);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File #" + years[i] +" "+ stNumber+ " has been failed.");
            String temp;
            lockStations.lock();
            temp = stations.pop().toString();
            lockStations.unlock();
            new Count(years, lockYears, temp, stations, lockStations);
        }
        }

        try {
            ftpClient.abort();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < years.length; i++) {

            //gzip q = new gzip();
            boolean success =gzip.main(stNumber+"/"+ stNumber + "-" + years[i]  + ".gz", stNumber+"/"+stNumber + "-" + years[i] );
            if (success)
            {
                Path path = Paths.get(stNumber+"/"+ stNumber + "-" + years[i]  + ".gz") ;
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    String temp;
                    lockStations.lock();
                    temp = stations.pop().toString();
                    lockStations.unlock();
                    new Count(years, lockYears, temp, stations, lockStations);
                }
            }else {System.out.println("File unpacking   #" + years[i] +" "+ stNumber+ " has been failed.");
                String temp;
                lockStations.lock();
                temp = stations.pop().toString();
                lockStations.unlock();
                new Count(years, lockYears, temp, stations, lockStations);
            }

        }
        String temp;
        lockStations.lock();
        temp = stations.pop().toString();
        lockStations.unlock();
        new Count(years, lockYears, temp, stations, lockStations);
        System.out.println("mythread run is over" );

    }
}