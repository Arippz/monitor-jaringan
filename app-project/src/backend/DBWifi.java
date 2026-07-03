package backend;

import java.util.ArrayList;
import java.util.Arrays;
public class DBWifi {
    public static ArrayList<ArrayList<String>> databaseWifi = new ArrayList<>();
    public static int[] jamSibukHistory = new int[24]; // Histori jumlah user per jam (0-23)

    // Menambahkan baris data (sebagai record)
    static {
        databaseWifi.add(new ArrayList<>(Arrays.asList("A1:B2:C3:D4", "John Doe", "40 MB", "Aktif", "00:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("E5:F6:G7:H8", "Budi Jatmiko", "40 MB", "Aktif", "00:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("BC:D1:12:F5", "Siti Aminah", "40 MB", "Aktif", "00:00")));
        databaseWifi.add(new ArrayList<>(Arrays.asList("D9:E0:F1:G2", "Walter White", "40 MB", "Aktif", "00:00")));
    }

    // Metode untuk merekam histori jam akses
    public static void recordAccessTime(int hour) {
        if (hour >= 0 && hour < 24) {
            jamSibukHistory[hour]++;
        }
    }

    // Metode untuk menghitung jumlah user aktif pada jam tertentu
    public static int countUsersByHour(int hour) {
        int count = 0;
        if (hour >= 0 && hour < 24) {
            for (ArrayList<String> row : databaseWifi) {
                if (row.size() > 4) {
                    try {
                        int userHour = Integer.parseInt(row.get(4).split(":")[0]);
                        if (userHour == hour) {
                            count++;
                        }
                    } catch (Exception ex) {
                        // skip invalid time
                    }
                }
            }
        }
        return count;
    }

    // Metode untuk menambahkan pengguna baru
    public static void addUser(String macAddress, String namaUser, String jamAkses) {
        ArrayList<String> newUser = new ArrayList<>();
        newUser.add(macAddress);
        newUser.add(namaUser);
        newUser.add("100MB"); // Kuota default 100 MB
        newUser.add("Aktif"); // Status default Aktif
        newUser.add(jamAkses);
        databaseWifi.add(newUser);
    }
}
