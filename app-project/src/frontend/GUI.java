package frontend;

import backend.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import backend.DBWifi; 


import java.awt.*;

public class GUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;

    private final String HALAMAN_UTAMA = "Manajemen Pengguna";
    private final String HALAMAN_TAMBAH = "Tambah Pengguna";

    public GUI() {
        Main.Perubahan();
        System.out.print(DBWifi.databaseWifi.get(1));
        System.out.print(DBWifi.databaseWifi.get(3));
        setTitle("Kelompok 1");
        setSize(650, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(createManajemenPanel(), HALAMAN_UTAMA);
        cardLayout.show(mainContainer, HALAMAN_UTAMA);

        add(mainContainer);
    }


    private JPanel createManajemenPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel("Manajemen Pengguna");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBounds(20, 20, 250, 25);
        panel.add(labelTitle);

        JButton btnTambahAtas = new JButton("+ Tambah Pengguna");
        btnTambahAtas.setBounds(20, 60, 160, 25);
        btnTambahAtas.setFont(new Font("Arial", Font.PLAIN, 12));
        btnTambahAtas.setBackground(Color.WHITE);
        
        btnTambahAtas.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_TAMBAH));
        panel.add(btnTambahAtas);


        JLabel labelAkses = new JLabel("Atur Akses Jaringan (Challenge)");
        labelAkses.setBounds(20, 100, 180, 25);
        panel.add(labelAkses);

        String[] statusOptions = {"Aktif (Full Speed)", "Limit (128Kbps)"};
        JComboBox<String> cbStatus = new JComboBox<>(statusOptions);
        cbStatus.setBounds(200, 100, 140, 25);
        panel.add(cbStatus);

        JButton buttonUpdateStatus = new JButton("Update Status Akses");
        buttonUpdateStatus.setBounds(345, 100, 150, 25);
        buttonUpdateStatus.setBackground(Color.WHITE);
        panel.add(buttonUpdateStatus);

        JTextField txtBuatCari = new JTextField("Cari nama pengguna...");
        txtBuatCari.setForeground(Color.GRAY);
        txtBuatCari.setBounds(20, 135, 150, 25);
        panel.add(txtBuatCari);

        JButton buttonCari = new JButton("Cari");
        buttonCari.setBounds(175, 135, 60, 25);
        buttonCari.setBackground(Color.WHITE);
        panel.add(buttonCari);

        JLabel labelMonitoring = new JLabel("Monitoring WiFi Publik");
        labelMonitoring.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMonitoring.setBounds(20, 175, 400, 20);
        panel.add(labelMonitoring);

        String[] columnNames = {"ID/MAC", "Nama Pengguna", "Kuota Sisa", "Status Jaringan", "Jam Akses"};
        Object[][] data = {}; 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 5)); 
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 580, 100);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane);


        JButton btnGrafik = new JButton("Tampilkan grafik");
        btnGrafik.setBounds(20, 315, 130, 25);
        btnGrafik.setBackground(Color.WHITE);
        panel.add(btnGrafik);

        JButton btnAnalisis = new JButton("Analisis Jam Sibuk");
        btnAnalisis.setBounds(20, 345, 150, 25);
        btnAnalisis.setBackground(Color.WHITE);
        panel.add(btnAnalisis);

        return panel; 
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}