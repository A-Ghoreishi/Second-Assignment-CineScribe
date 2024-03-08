import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class Movie {
    public static final String API_KEY = "8f7e8531";   // TODO --> add your api key about Movie here
    int ImdbVotes;
    ArrayList<String> actorsList;
    String rating;

    public Movie(ArrayList<String> actorsList, String rating, int ImdbVotes){
        //TODO --> (Write a proper constructor using the get_from_api functions)
        ImdbVotes = 0;
        rating = null;
        actorsList = new ArrayList<>();
        
    }

    @SuppressWarnings("deprecation")
    /**
     * Retrieves data for the specified movie.
     *
     * @param title the name of the title for which MovieData should be retrieved
     * @return a string representation of the MovieData, or null if an error occurred
     */

    public String getMovieData(String title) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?t="+title+"&apikey="+API_KEY);
        URLConnection Url = url.openConnection();
        Url.setRequestProperty("Authorization", "Key" + API_KEY);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Url.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine())!=null) {
            stringBuilder.append(line);
        }
        reader.close();
        //handle an error if the chosen movie is not found
        return stringBuilder.toString();
    }
    public int getImdbVotesViaApi(String moviesInfoJson) throws IOException{

        //TODO --> (This function must change and return the "ImdbVotes" as an Integer)
        // NOTICE :: you are not permitted to convert this function to return a String instead of an int !!!
        JSONObject IMDB = new JSONObject(moviesInfoJson);
        int ImdbVotes = Integer.parseInt(IMDB.getString("imdbVotes").replace(",", ""));
        return ImdbVotes;
    }

    public String getRatingViaApi(String moviesInfoJson) {
        try {
            JSONObject jsonObject = new JSONObject(moviesInfoJson);


            JSONArray ratingsArray = jsonObject.getJSONArray("Ratings");


            for (int i = 0; i < ratingsArray.length(); i++) {
                JSONObject ratingObject = ratingsArray.getJSONObject(i);


                if ("Internet Movie Database".equals(ratingObject.getString("Source"))) {

                    String rating = ratingObject.getString("Value");
                    System.out.println("IMDB Rating: " + rating);
                    return rating;
                }
            }

            System.out.println("IMDB Rating not found");
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void getActorListViaApi(String movieInfoJson) {
        try {
            JSONObject jsonObject = new JSONObject(movieInfoJson);


            JSONArray actorsArray = jsonObject.getJSONArray("Actors");

            System.out.println("Actors: {");
            for (int i = 0; i < actorsArray.length(); i++) {
                String actorName = actorsArray.getString(i);
                System.out.println("      " + actorName);
            }
            System.out.println("}");

        } catch (JSONException e) {

            try {
                String singleActor = new JSONObject(movieInfoJson).getString("Actors");
                System.out.println("Actors: {" + singleActor + "}");
            } catch (JSONException innerException) {
                // Handle the error gracefully
                innerException.printStackTrace();
                System.out.println("Error parsing Actors data");
            }
        }
    }

}