import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
public class Actors {
    public static final String API_KEY = "KCQ67rWmRfOVK4hJh4m5oA==97RQ9O9190tc1sp7";   // TODO --> add your api key about Actors here
    String netWorth;
    Boolean isAlive;

    public Actors(String netWorth, boolean isAlive){
        //TODO --> (Write a proper constructor using the get_from_api functions)

        netWorth = "";
        isAlive = true;
    }
    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name="+
                    name.replace(" ", "+")+"&apikey="+API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public double getNetWorthViaApi(String actorsInfoJson){
        //TODO --> (This function must return the "NetWorth")
        JSONObject jsonObject = new JSONObject(API_KEY);
        JSONObject net_worth = jsonObject.getJSONObject("net_worth");
        double result = 0.0;
        String netWorth = jsonObject.getString("net_worth");
        result =  Double.parseDouble(netWorth);
        System.out.print("Net Worth is: ");
        return result;
    }

    public boolean isAlive(String actorsInfoJson){
        //TODO --> (If your chosen actor is alive it must return true otherwise it must return false)
        boolean statues = false;
        JSONObject jsonObject = new JSONObject(API_KEY);
        JSONObject Alive = jsonObject.getJSONObject("is_alive");
        boolean isAlive = Alive.getBoolean("is_alive");

        if(isAlive){
            getDateOfDeathViaApi(actorsInfoJson);
        }

        else
            statues = true;

        return statues;
    }

    public String getDateOfDeathViaApi(String actorsInfoJson){
        //TODO --> (If your chosen actor is deceased it must return the date of death)  -->
        JSONObject jsonObject = new JSONObject(API_KEY);
        JSONObject Date = jsonObject.getJSONObject("death");
        String date = "";
        date = Date.getString("death");
        return date;
    }

}