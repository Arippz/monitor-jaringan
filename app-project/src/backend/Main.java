package backend;
import java.util.Random;
import frontend.GUI;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public ArrayList<String> row = new ArrayList<String>();
    public static void main(String[] args){
    }
    public void Perubahan(GUI MyGui){
        Random random = new Random();

        for (int i = 0; i < 1; i++) { 
            MyGui.clearTable();     
            boolean allUsersEmpty = true;

            for (ArrayList<String> r : DBWifi.databaseWifi) {
                this.row = r;
                int currentNumber = Integer.parseInt(this.row.get(2).replaceAll("[^0-9]", ""));

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
                            this.row.set(3, "DIBLOKIR"); 
                        }
                        this.row.set(2, currentNumber + " MB");

                        // 3. UPDATE TIME ONLY IF DATA WAS ACTUALLY CONSUMED
                        int lastIndex = this.row.size() - 1;
                        String[] timeParts = this.row.get(lastIndex).split(":");
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
                        System.out.println("[TRAFFIC] " + this.row.get(1));
                    } else {
                        // User is idle during this specific second tick
                        System.out.println("[IDLE] " + this.row.get(1) + " sedang tidak memakai intenet.");
                    }
                }
                // Print current snapshot status for this user
                System.out.println("Kouta sisa: " + this.row.get(2) + " | Status: " + this.row.get(3) + " | Jam Akses: " + this.row.get(this.row.size() - 1) + "\n");
                Object[] tableRecord = {
                    this.row.get(0),
                    this.row.get(1),
                    this.row.get(2),
                    this.row.get(3),
                    this.row.get(4),
                };
                MyGui.addRecord(tableRecord);
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
    public static void createData(String mac, String nama, String kuota, String status, String jam) {
            ArrayList<String> dataBaru = new ArrayList<>(Arrays.asList(mac, nama, kuota, status, jam));
            DBWifi.databaseWifi.add(dataBaru);
            System.out.println("Berhasil menambahkan data: " + nama);
    }
}

