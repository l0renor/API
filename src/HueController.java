import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HueController {
    private final static String BASE = "http://localhost:80/api/newdeveloper";
    private final static String GROUP = BASE+"/groups/1";
    private final static String ACTION = GROUP+"/action";
    private final static String LIGHTS = BASE+"/lights";
    private final static String STATE = "/state";

    private final static String PUT = "PUT";

    private final static String OFF = "{ 'on': true, 'bri': 0 }";
    private final static String WHITE = "{ 'on': true, 'hue': 0, 'sat': 0, 'bri': 200 }";
    private final static String ORANGE = "{ 'on': true, 'hue': 4500, 'sat': 230, 'bri': 200 }";
    private final static String RED = "{ 'on': true, 'hue': 0, 'sat': 254, 'bri': 254 }";

    public HueController() {
        String json = "{ 'lights': ['1','2','3'] }";
        sendRequest(GROUP, json);
        json = WHITE;
        sendRequest(ACTION, json);
    }

    /**
     * Wechselt das Lichtverhalten
     * @param number Entweder 1-3 für eine einzelne Lampe oder 0 für alle.
     * @param type 0: AUS, 1: WEIß, 2: ORANGE, 3: ROT
     */
    public void changeLight(int number, int type) {
        String base;
        String body;
        if (number == 0) {
            base = ACTION;
        } else {
            base = LIGHTS+"/"+number+STATE;
        }
        switch (type) {
            case 0:
                body = OFF;
                break;
            case 1:
                body = WHITE;
                break;
            case 2:
                body = ORANGE;
                break;
            case 3:
                body = RED;
                break;
            default:
                return;
        }
        sendRequest(base, body);

    }

    private void sendRequest(String base, String body) {
        try{
            URL url = new URL(base);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(PUT);
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write("\r\n");
            os.write(body);
            os.close();
            httpURLConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
