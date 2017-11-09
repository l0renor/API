import POJO.GoogleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class TCPClient3 {
	public static final String KEY = "&key=AIzaSyCliFRJH6LCSZS77OhwNHccm3Y1FOAgkQg";
	public static final String BASE = "https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric&origins= Lothstraße+6,Muenchen&";
	
	public static void main(String[] args) throws IOException {
		TCPClient3 client = new TCPClient3();
		HashMap<String,String> test = new HashMap<>();
		test.put("destinations","Haimhausen,Germany");
		test.put("origins" ,"Lothstraße+6,Muenchen");
		String antwort = client.doRequest("https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric",test);

		ObjectMapper mapper = new ObjectMapper();
		GoogleResponse json = mapper.readValue(antwort,GoogleResponse.class);
		System.out.println(json);
		System.out.println(json.getRows()[0].getElements()[0].getDuration().getText());
	}

	/**
	 * does a https get request
	 * @param base standart adress
	 * @param params parameters to be added
	 * @return
	 */
	public String doRequest(String base,HashMap<String ,String> params){
		String url = base;
		String result = "";
		for(Map.Entry<String,String> e:params.entrySet()){
			String k = e.getKey();
			String v = e.getValue();
			url = url + "&"+k+"="+v;
		}
		url+=KEY;
		HttpURLConnection con;
		try {
			con = (HttpsURLConnection)
					new URL(url).openConnection();
			try(	
					InputStream input = con.getInputStream();
	
					BufferedReader fromServer =
							new BufferedReader(
									new InputStreamReader(
											input))){
				
				for(String line=fromServer.readLine();
						line !=null /*&& line.length()>0*/;
						line = fromServer.readLine()){
						//System.out.println(line);
					result+= line+"\r\n";
				}
				//System.out.println(result);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return result;
	}
	
	
}
