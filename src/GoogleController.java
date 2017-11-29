import POJO.GoogleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class GoogleController {

    public static final String BASEGOOGLE = "https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric";
    public static final String START = "Lothstraße+64+München";

    /**
     * Leon Lukas
     * Does Rest request to get travel time.
     * @param goal goal
     * @param verkehrsmittel genutzes Verkehrsmittel
     * @return the traveltime in seconds
     */
    public static int getTravelduration(String goal, String verkehrsmittel){
        try{

        TCPClient3 client = new TCPClient3();
        HashMap<String,String> params = new HashMap<>();
        params.put("destinations",goal);
        params.put("origins" ,START);
        params.put("mode" ,verkehrsmittel);
        String antwort = client.doRequest("https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric",params);

        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse json = null;
        try {
            json = mapper.readValue(antwort, GoogleResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Integer.parseInt(json.getRows()[0].getElements()[0].getDuration().getValue());
    } catch (Exception e){
            System.out.println("Fehlerhafte eingabe der Google Daten");
            return -1;
        }
}}
