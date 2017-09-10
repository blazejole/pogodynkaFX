package pogodynka;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static String readWebsideContex(String url){
StringBuilder builder = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection =(HttpURLConnection) new URL(url).openConnection();
            InputStream i = httpURLConnection.getInputStream();
            int response = 0;
            while((response = i.read()) != -1){
                builder.append((char)response);
            }
            i.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
