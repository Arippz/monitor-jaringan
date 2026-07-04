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

                    
                    boolean isConsumingData = random.nextBoolean(); 

                    if (isConsumingData) {
                        
                        int dataSpent = random.nextInt(3) + 1; 
                        currentNumber -= dataSpent;
                        
                        if (currentNumber <= 0) {
                            currentNumber = 0;
                            this.row.set(3, "DIBLOKIR"); 
                        }
                        this.row.set(2, currentNumber + " MB");

                        
                        int lastIndex = this.row.size() - 1;
                        String[] timeParts = this.row.get(lastIndex).split(":");
                        int hours = Integer.parseInt(timeParts[0]);
                        int minutes = Integer.parseInt(timeParts[1]);
                        int oldHours = hours;

                       
                        minutes += random.nextInt(60) + 1; 
                        if (minutes >= 60) {
                            hours += minutes / 60;
                            minutes = minutes % 60;
                        }
                        
                       
                        if (hours != oldHours && hours < 24) {
                            DBWifi.recordAccessTime(hours);
                        }
                        
                        row.set(lastIndex, String.format("%02d:%02d", hours, minutes));
                        
                       
                        System.out.println("[TRAFFIC] " + this.row.get(1));
                    } else {
                        
                        System.out.println("[IDLE] " + this.row.get(1) + " sedang tidak memakai intenet.");
                    }
                }
                
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
                break; 
            }

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
}

