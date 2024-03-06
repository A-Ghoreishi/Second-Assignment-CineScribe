import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome. Please choose:");
        System.out.println("Movie");
        System.out.println("Celebrity");
        runMenu();
    }
    public static void runMenu() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        if(str == "Movie"){
            Scanner scanner1 = new Scanner(System.in);
            String str1 = scanner1.next();
            Movie Item = new Movie(new ArrayList<>(),"",0);
        }

        if(str == "Celebrity"){
            Scanner scanner1 = new Scanner(System.in);
            String str1 = scanner1.next();
            Actors Item = new Actors("",true);
        }
    }
}