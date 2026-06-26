import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<PenggunaWiFi> databaseWifi = new ArrayList<>();

        databaseWifi.add(new PenggunaWiFi("A1:B2:C3:D4", "John Doe", "120 MB", "Aktif", "10:00"));
        databaseWifi.add(new PenggunaWiFi("E5:F6:G7:H8", "Budi Jatmiko", "45 MB", "Limit", "14:00"));
        databaseWifi.add(new PenggunaWiFi("BC:D1:12:F5", "Siti Aminah", "0 MB", "DIBLOKIR", "14:00"));

        for (PenggunaWiFi p : databaseWifi) {
            System.out.println("Nama: " + p.nama + " | Status: " + p.status);
        }



    public void Perubahan(i){
        while 
    }   
    }
}
