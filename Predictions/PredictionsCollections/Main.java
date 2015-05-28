import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import org.apache.commons.io.*;

public class Main {

    public static void main(final String[] args) {
	// write your code here
        final String homeDir= System.getProperty("user.dir")+"/predictions2/";
        System.out.println(homeDir);
        File file = new File(homeDir);


        final File tmax = new File("tmax.txt");
        final File tmin = new File("tmin.txt");
        final File pressure = new File("pressure.txt");
        //int i=0;
        final String Date = args[0];


        final String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
      //  Stack<String[]> files = new Stack<String[]>();
        for(int i=0; i< directories.length; i++){
            System.out.println(homeDir  + directories[i]);
            File filei = new File(homeDir  +directories[i]);

            final String[] filesInCurrentDirectory = filei.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {

                    if(new File(current, name).isFile() &&  name.contains("tmax")&& name.contains(Date)){

                        //Path path = Paths.get(current.toPath() + "/" + name);
                       // inputStream = new FileInputStream("619760-99999_finish.out");



                        BufferedReader reader = null;
                        try {
                            System.out.println(homeDir+current.getName() + "/" + name);
                            reader = new BufferedReader(new FileReader(homeDir+current.getName()+"/"+name));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        List<String> lines = new ArrayList<String>();
                        String line = null;
                        try {
                            while ((line = reader.readLine()) != null) {
                                lines.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //System.out.println());

                        try {
                            FileUtils.writeStringToFile(tmax, current.getName() +";"+ lines.get(0)+  "\n", true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                            System.out.println("string was added");
                            return false;

                    }

                    if(new File(current, name).isFile() &&  name.contains("tmin")&& name.contains(Date)){


                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new FileReader(homeDir+current.getName()+"/"+name));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        List<String> lines = new ArrayList<String>();
                        String line = null;
                        try {
                            while ((line = reader.readLine()) != null) {
                                lines.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //System.out.println());

                        try {
                            FileUtils.writeStringToFile(tmin, current.getName() +";"+ lines.get(0)+  "\n", true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("string was added");
                        return false;
                    }

                    if(new File(current, name).isFile() &&  name.contains("pressure")&& name.contains(Date)){

                        //Path path = Paths.get(current.toPath() + "/" + name);
                        // inputStream = new FileInputStream("619760-99999_finish.out");



                        BufferedReader reader = null;
                        try {
                            System.out.println(homeDir+current.getName() + "/" + name);
                            reader = new BufferedReader(new FileReader(homeDir+current.getName()+"/"+name));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        List<String> lines = new ArrayList<String>();
                        String line = null;
                        try {
                            while ((line = reader.readLine()) != null) {
                                lines.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //System.out.println());

                        try {
                            FileUtils.writeStringToFile(pressure, current.getName() +";"+ lines.get(0)+  "\n", true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("string was added");
                        return false;

                    }

                    return new File(current, name).isFile();
                }
            });
           // files.push(filesInCurrentDirectory);

        }



    }
}
