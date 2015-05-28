/**
 * Created by user on 02.05.2015.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class gzip
{

    public static void main( String fileIn, String fileout)
    {
        gzip gZip = new gzip();
        gZip.gunzipIt(fileIn, fileout);
    }

    /**
     * GunZip it
     */
    public void gunzipIt(String fileIn, String fileout){

        byte[] buffer = new byte[1024];

        try{

            GZIPInputStream gzis =
                    new GZIPInputStream(new FileInputStream(fileIn));

            FileOutputStream out =
                    new FileOutputStream(fileout);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
