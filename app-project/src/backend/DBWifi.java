package backend;

import java.util.ArrayList;
import java.util.Arrays;
public class DBWifi {
    public static ArrayList<ArrayList<String>> databaseWifi = new ArrayList<>();

    // Menambahkan baris data (sebagai record)
    static {
        databaseWifi.add(new ArrayList<>(Arrays.asList("A1:B2:C3:D4", "John Doe", "10 MB", "Aktif", "10:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("E5:F6:G7:H8", "Budi Jatmiko", "10 MB", "Limit", "10:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("BC:D1:12:F5", "Siti Aminah", "10 MB", "DIBLOKIR", "10:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("D9:E0:F1:G2", "User Baru", "10 MB", "Aktif", "10:00")));
    }

    // Metode untuk menambahkan pengguna baru
    public static void addUser(String macAddress, String namaUser, String jamAkses) {
        ArrayList<String> newUser = new ArrayList<>();
        newUser.add(macAddress);
        newUser.add(namaUser);
        newUser.add("10 MB"); // Kuota default 100 MB
        newUser.add("Aktif"); // Status default Aktif
        newUser.add(jamAkses);
        databaseWifi.add(newUser);
    }
}
