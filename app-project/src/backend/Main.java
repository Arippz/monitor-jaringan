package backend;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) {
        Perubahan();
    }
    public static void Perubahan(){
        for (int i = 0; i < 5; i++) {
            System.out.println("\n--- Second " + (i + 1) + " ---");

            for (ArrayList<String> row : DBWifi.databaseWifi) {
                int currentNumber = Integer.parseInt(row.get(2).replaceAll("[^0-9]", ""));
                row.set(2, (currentNumber + 3) + " MB");
                System.out.println(row.get(1) + ": " + row.get(2));
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
}

