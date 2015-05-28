import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        File file = new File(System.getProperty("user.dir")+"/src/dataset/");
        System.out.println(System.getProperty("user.dir")+"/src/dataset/");

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/filename.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(inputStream, "UTF-8");
        final String[] directoriesRight = new String[1041];
        int t =0;
        while (sc.hasNextLine()){

          directoriesRight[t]= sc.nextLine();
            t+=1;
        }

        //reading all directories names in array
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                if(Arrays.asList(directoriesRight).contains(name)){
                return new File(current, name).isDirectory();
                }else {
                    try {

                        System.out.println("delete directory" + System.getProperty("user.dir")+"/src/dataset/"+name);
                        FileUtils.deleteDirectory(new File(current.getPath()+"/"+name));
                        //Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/src/dataset/" + name));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        //reading all inner files in stack
        Stack <String[]> files = new Stack<String[]>();
        //Queue<String[]> files = new LinkedList<String[]>();
        for(int i=0; i< directories.length; i++){
            File filei = new File(System.getProperty("user.dir")+"/src/dataset/"  +directories[i]);


            String[] filesInCurrentDirectory = filei.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    if(new File(current, name).isFile() &&  name.contains("prefinal")){

                      Path path = Paths.get(current.toPath()+"/"+name);
                        try {
                            System.out.println("File to be deleted" + path);
                            Files.deleteIfExists(path);
                            return false;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return new File(current, name).isFile();
                }
            });
                    files.push(filesInCurrentDirectory);

        }


        InputStream input = null;

        OutputStream output = null;

        for(int i=0; i<directories.length; i++){

            File dest = new File(System.getProperty("user.dir")+"/src/dataset/"+ directories[i]+"/" + directories[i]+"_prefinal.out");
            try {
                output = new FileOutputStream(dest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String[] filesList = files.peek();

            // formating files using changed tool of NOAA
            for (int j = 0; j < filesList.length; j++){
                ishJava.main(new String[]{System.getProperty("user.dir")+"/src/dataset/" + directories[i] +"/" + filesList[j], System.getProperty("user.dir")+"/src/dataset/" + directories[i] +"/" + filesList[j]+".out"});
                try {
                    Files.deleteIfExists(Paths.get(System.getProperty("user.dir")+"/src/dataset/" + directories[i] +"/" + filesList[j]) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < filesList.length; j++) {

                // FileReader reader = new FileReader(st[t] + "-99999-" + i + ".out");


                File source = new File(System.getProperty("user.dir")+"/src/dataset/"+ directories[i]+"/" + filesList[j]+".out");
                try {
                    input = new FileInputStream(source);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                byte[] buf = new byte[1024];
                int bytesRead;

                assert input != null;
                try {
                    while ((bytesRead = input.read(buf)) > 0) {

                        output.write(buf, 0, bytesRead);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Files.deleteIfExists(Paths.get(System.getProperty("user.dir")+"/src/dataset/"+ directories[i]+"/" + filesList[j]+".out"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(i);
            format(System.getProperty("user.dir") + "/src/dataset/" + directories[i] + "/" + directories[i] + "_prefinal.out", System.getProperty("user.dir") + "/src/dataset/" + directories[i] + "/" + directories[i] + "_final.out", directories[i], System.getProperty("user.dir") + "/src/dataset/");

        }
       System.out.println(Arrays.toString(directories));




    }

//method for format text files( change , with .; choosing lines with choosen time)
    public static boolean format(String input, String output, String dirname, String homedir){
        FileInputStream inputStream = null;
        Scanner sc = null;
        Writer writer;
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



            //Path path = Paths.get(out.getPath());
            //Charset charset = StandardCharsets.UTF_8;

      //      String content = new String(Files.readAllBytes(path), charset);
        //    content = content.replaceAll(",", ".");
          //  Files.write(path, content.getBytes(charset));
            // note that Scanner suppresses exceptions

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // try {
            //Files.deleteIfExists(Paths.get(input));
      //  } catch (IOException e) {
       //     e.printStackTrace();
      //  }
        return true;
    }

}
