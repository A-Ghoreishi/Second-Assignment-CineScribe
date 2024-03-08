import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.fasterxml.jackson.databind.ObjectMapper;
public class Actors {
    public static final String API_KEY = "KCQ67rWmRfOVK4hJh4m5oA==97RQ9O9190tc1sp7";   // TODO --> add your api key about Actors here
    String netWorth;
    Boolean isAlive;

    public Actors(String netWorth, boolean isAlive) {
        //TODO --> (Write a proper constructor using the get_from_api functions)

        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name=" +
                    name.replace(" ", "+") + "&apikey=" + API_KEY);
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
                return response.toString().substring(1,response.toString().length()-1);
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getNetWorthViaApi(String actorsInfoJson) throws IOException {

        double result = 0;
        try {
            JSONArray net_worth = null;
            if (actorsInfoJson.startsWith("[")) {
                net_worth = new JSONArray(actorsInfoJson);
                for (int i = 0; i < net_worth.length(); i++) {
                    JSONObject jsonObject = net_worth.getJSONObject(i);

                    if(jsonObject.has("net_worth")){
                        int NetWorth = jsonObject.getInt("net_worth");

                        result += (double) NetWorth;
                    }
                }
            }

            else{
                JSONObject jsonObject = new JSONObject(actorsInfoJson);

                if(jsonObject.has("net_worth")){
                    int NetWorth = jsonObject.getInt("net_worth");

                    result += (double) NetWorth;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }




    public boolean isAlive(String actorsInfoJson) throws IOException {
        boolean isAlive = false;
        try {
                // Handle JSON object
                JSONObject aliveObject = new JSONObject(actorsInfoJson);
                isAlive = aliveObject.optBoolean("is_alive", false);

                if (isAlive) {
                    String dateOfDeath = getDateOfDeathViaApi(actorsInfoJson);
                    if (dateOfDeath != null) {
                        System.out.println("Date of Death: " + dateOfDeath);
                    } else {
                        System.out.println("Date of Death not available.");
                    }
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isAlive;
    }




    public String getDateOfDeathViaApi(String actorsInfoJson) {
        String date = null;

        try {
            if (actorsInfoJson.startsWith("[")) {
                // Handle JSON array
                JSONArray jsonArray = new JSONArray(actorsInfoJson);
                // Decide how to handle the array data
            } else {
                // Handle JSON object
                JSONObject dateObject = new JSONObject(actorsInfoJson);

                // Check if the "death" field exists
                if (dateObject.has("death")) {
                    date = dateObject.getString("death");
                    System.out.println("Date of Death: " + date);
                } else {
                    System.out.println("No date of death information available.");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return date;
    }

}