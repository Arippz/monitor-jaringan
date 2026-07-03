package backend;
import java.util.Random;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        
    }
    public static void Perubahan(){
        Random random = new Random();

        for (int i = 0; i < 5; i++) { // Increased to 15 seconds to allow staggered events to unfold
            System.out.println("\n=== TIME TICK: Second " + (i + 1) + " ===");

            boolean allUsersEmpty = true;

            for (ArrayList<String> row : DBWifi.databaseWifi) {
                int currentNumber = Integer.parseInt(row.get(2).replaceAll("[^0-9]", ""));

                if (currentNumber > 0) {
                    allUsersEmpty = false; 

                    // 1. GENERATE A RANDOM EVENT
                    // nextBoolean() gives a 50/50 chance. 
                    // Only consume data if true (simulating active network traffic for this user)
                    boolean isConsumingData = random.nextBoolean(); 

                    if (isConsumingData) {
                        // 2. RANDOM QUANTITY (Optional)
                        // Randomly subtract 1, 2, or 3 MB instead of a flat 3 MB
                        int dataSpent = random.nextInt(3) + 1; 
                        currentNumber -= dataSpent;
                        
                        if (currentNumber <= 0) {
                            currentNumber = 0;
                            row.set(3, "DIBLOKIR"); 
                        }
                        row.set(2, currentNumber + " MB");

                        // 3. UPDATE TIME ONLY IF DATA WAS ACTUALLY CONSUMED
                        int lastIndex = row.size() - 1;
                        String[] timeParts = row.get(lastIndex).split(":");
                        int hours = Integer.parseInt(timeParts[0]);
                        int minutes = Integer.parseInt(timeParts[1]);

                        // Time advances randomly between 1 and 4 minutes when they are active
                        minutes += random.nextInt(4) + 1; 
                        if (minutes >= 60) {
                            hours += minutes / 60;
                            minutes = minutes % 60;
                        }
                        row.set(lastIndex, String.format("%02d:%02d", hours, minutes));
                        
                        // Alert the terminal of an active data event
                        System.out.println("[TRAFFIC] " + row.get(1));
                    } else {
                        // User is idle during this specific second tick
                        System.out.println("[IDLE] " + row.get(1) + " sedang tidak memakai intenet.");
                    }
                }

                // Print current snapshot status for this user
                System.out.println("Kouta sisa: " + row.get(2) + " | Status: " + row.get(3) + " | Jam Akses: " + row.get(row.size() - 1) + "\n");
            }

            if (allUsersEmpty) {
                System.out.println("Semua pengguna telah kehabisan kuota. Menghentikan simulasi...");
                break; 
            }

            try {
                Thread.sleep(1000); // 1-second interval between network checks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
}

