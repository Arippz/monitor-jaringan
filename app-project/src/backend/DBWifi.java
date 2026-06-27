package backend;

import java.util.ArrayList;
import java.util.Arrays;
public class DBWifi {
    public static ArrayList<ArrayList<String>> databaseWifi = new ArrayList<>();

    // Menambahkan baris data (sebagai record)
    static {
        databaseWifi.add(new ArrayList<>(Arrays.asList("A1:B2:C3:D4", "John Doe", "120 MB", "Aktif", "10:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("E5:F6:G7:H8", "Budi Jatmiko", "45 MB", "Limit", "14:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("BC:D1:12:F5", "Siti Aminah", "0 MB", "DIBLOKIR", "14:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("D9:E0:F1:G2", "User Baru", "10 MB", "Aktif", "15:00")));
    }
}
