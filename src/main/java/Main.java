import java.io.IOException;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome. Please choose:");
        System.out.println("Movie");
        System.out.println("Celebrity");

        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();

        runMenu(str);
    }

    public static void runMenu(String str) throws IOException {


        if ("Movie".equalsIgnoreCase(str)) try {

         Movie movieItem = new Movie(new ArrayList<>(), null, 0);

         Scanner scanner1 = new Scanner(System.in);
         System.out.println("Enter movie title:");
         String movieTitle = scanner1.next();

         String movieData = movieItem.getMovieData(movieTitle);
         int imdbVotes = movieItem.getImdbVotesViaApi(movieData);
         String rating = movieItem.getRatingViaApi(movieData);

         System.out.println("IMDB Votes: " + imdbVotes);

         movieItem.getActorListViaApi(movieData);
         } catch (IOException e) {
         e.printStackTrace();
         }



        if ("Celebrity".equalsIgnoreCase(str)) {

            Actors actorItem = new Actors(null, true);

            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Enter actor name:");
            String actorName = scanner1.next();

            String actorData = actorItem.getActorData(actorName);
            double netWorth = actorItem.getNetWorthViaApi(actorData);
            boolean isAlive = actorItem.isAlive(actorData);

            System.out.println("Net Worth: " + netWorth);
            System.out.println("Is Alive: " + isAlive);

            if (isAlive) {
                String dateOfDeath = actorItem.getDateOfDeathViaApi(actorData);
                if (dateOfDeath != null) {
                    System.out.println("Date of Death: " + dateOfDeath);
                } else {
                    System.out.println("Date of Death not available.");
                }
            }
        }
    }
}

