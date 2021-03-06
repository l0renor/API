import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Fabian Reinold
 */
public class HueController {
    private final static String BASE = "http://localhost:80/api/newdeveloper";
    private final static String GROUP = BASE+"/groups/1";
    private final static String ACTION = GROUP+"/action";
    private final static String LIGHTS = BASE+"/lights";
    private final static String STATE = "/state";

    private final static String PUT = "PUT";

    /**
     * Initiates Controller and groups the lamps and turns them white
     */
    public HueController() {
        String json = "{ \"lights\": [\"1\",\"2\",\"3\"] }";
        sendRequest(GROUP, json);
        json = Color.WHITE.getJsonMessage();
        sendRequest(ACTION, json);
    }

    /**
     * Wechselt das Lichtverhalten
     * @param number Entweder 1-3 für eine einzelne Lampe oder 0 für alle.
     * @param color Das Enum Color
     */
    public void changeLight(int number, Color color) {
        String base;
        if (number == 0) {
            base = ACTION;
        } else {
            base = LIGHTS + "/" + number + STATE;
        }
        sendRequest(base, color.getJsonMessage());

    }

    /**
     * Sendet einen Request an die Hue-Bridge.
     * @param base  Komplette URL.
     * @param body  JSON Body zur Verarbeitung der Hue-Lampen
     */
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
            int rc = httpURLConnection.getResponseCode();
            if(rc != 200) {
                System.out.println("Es gab Probleme bei der Verbindung zur Hue Bridge");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
