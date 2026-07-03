package backend;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Perubahan();
    }
    public static void Perubahan(){
        for (int i = 0; i < 5; i++) {
            System.out.println("\n--- Test " + (i + 1) + " ---");

            // Track if ALL users are out of quota during this second
            boolean allUsersEmpty = true;

            for (ArrayList<String> row : DBWifi.databaseWifi) {
                int currentNumber = Integer.parseInt(row.get(2).replaceAll("[^0-9]", ""));

                // If the user still has quota, process them
                if (currentNumber > 0) {
                    allUsersEmpty = false; // At least one person still has internet!

                    // Decrease quota but don't let it go below 0
                    currentNumber -= 3;
                    if (currentNumber <= 0) {
                        currentNumber = 0;
                        row.set(3, "DIBLOKIR"); // Automatically block them when they hit 0
                    }
                    row.set(2, currentNumber + " MB");

                    // Process Time Update
                    int lastIndex = row.size() - 1;
                    String timeString = row.get(lastIndex);
                    String[] timeParts = timeString.split(":");
                    int hours = Integer.parseInt(timeParts[0]);
                    int minutes = Integer.parseInt(timeParts[1]);

                    minutes += 3;
                    if (minutes >= 60) { // Optional fix: handle hour rollover if minutes exceed 59
                        hours += minutes / 60;
                        minutes = minutes % 60;
                    }

                    String newTime = String.format("%02d:%02d", hours, minutes);
                    row.set(lastIndex, newTime);
                }

                // Print status for everyone (even if skipped/blocked)
                System.out.println(row.get(1) + ": " + row.get(2));
                System.out.println("Status : " + row.get(3));
                System.out.println(row.get(1) + " updated time: " + row.get(row.size() - 1) + "\n");
            }

            // BREAK LOGIC: If everyone is at 0 MB, kill the 5-second loop early
            if (allUsersEmpty) {
                System.out.println("Semua pengguna telah kehabisan kuota. Menghentikan simulasi...");
                break; 
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(DBWifi.databaseWifi.get(3));
            System.out.println(DBWifi.databaseWifi.get(2));
        }    
    }   
    public static void createData(String mac, String nama, String kuota, String status, String jam) {
            ArrayList<String> dataBaru = new ArrayList<>(Arrays.asList(mac, nama, kuota, status, jam));
            DBWifi.databaseWifi.add(dataBaru);
            System.out.println("Berhasil menambahkan data: " + nama);
    }
}

