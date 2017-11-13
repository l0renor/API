import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
//Bsp args: Agnes-Pockels-Bogen+21+80992+München 8:15 bicycling Ingolstädter+Str.+38+80992+München 10:00 driving Bunzlauer+Str.+8+80992+München 8:30 transit
public class Main {
    public static final String BASEGOOGLE = "https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric";
    public static final String BROWSERURL = "https://www.google.de/maps/dir/Lothstraße+6,+80335+München/";

    public static void main(String []args ) throws InterruptedException, URISyntaxException, IOException {
        List<String > ort = new LinkedList<>();
        List<String> zeit = new LinkedList<>();
        List<String> verkehrsmittel = new LinkedList<>();
        int[] durations = new int[3];

        for (int i = 0;i<9;i = i +3){
            ort.add(args[i]);
            zeit.add(args[i+1]);
            verkehrsmittel.add(args[i+2]);
        }
        if(Desktop.isDesktopSupported())
        {
            for (int i=0;i<3;i++) {
                String uri = BROWSERURL + ort.get(i);
                Desktop.getDesktop().browse(new URI(uri));
            }
        }
        while(true){
            for (int i = 0;i<3;i++) {
                durations[i] = GoogleController.getTravelduration(ort.get(i), verkehrsmittel.get(i));
            }
            System.out.println(durations[0]);
            System.out.println(durations[1]);
            System.out.println(durations[2]);
            TimeUnit.SECONDS.sleep(5);
        }
    }

}
