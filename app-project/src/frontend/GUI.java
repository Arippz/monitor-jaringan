package frontend;

import backend.DBWifi;
import backend.Main;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class GUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private DefaultTableModel model; 
    private JTable table;
    private final String HALAMAN_UTAMA = "Manajemen Pengguna";
    private final String HALAMAN_TAMBAH = "Tambah Pengguna";

    public GUI() {
        System.out.print(DBWifi.databaseWifi.get(1));
        System.out.print(DBWifi.databaseWifi.get(3));
        setTitle("Kelompok 1");
        setSize(650, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(createManajemenPanel(), HALAMAN_UTAMA);
        
        cardLayout.show(mainContainer, HALAMAN_UTAMA);

        add(mainContainer);

        for (ArrayList<String> r : DBWifi.databaseWifi){
            Object[] defaultRecord = {
                r.get(0),
                r.get(1),
                r.get(2),
                r.get(3),
                r.get(4)
            };
            this.addRecord(defaultRecord);
        }
    }

    public void clearTable() {
        this.model.setRowCount(0);
    }

    public void addRecord(Object[] recordData){
        this.model.addRow(recordData);
    }



    private JPanel createManajemenPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel("Manajemen Pengguna");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBounds(20, 0, 250, 25);
        panel.add(labelTitle);

        JButton btnTambahAtas = new JButton("+ Tambah Pengguna");
        btnTambahAtas.setBounds(20, 30, 160, 25);
        btnTambahAtas.setFont(new Font("Arial", Font.PLAIN, 12));
        btnTambahAtas.setBackground(Color.WHITE);
        
        btnTambahAtas.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_TAMBAH));
        panel.add(btnTambahAtas);


        JButton buttonUpdateStatus = new JButton("Update Status Akses");
        buttonUpdateStatus.setBounds(200, 30, 160, 25);
        buttonUpdateStatus.setBackground(Color.WHITE);
        panel.add(buttonUpdateStatus);

        buttonUpdateStatus.addActionListener(e -> {
            buttonUpdateStatus.setEnabled(false);
            Main backend = new Main();
            
            new Thread(() -> {
                backend.Perubahan(this);
                
                SwingUtilities.invokeLater(() -> buttonUpdateStatus.setEnabled(true));
            }).start();

        });


        JLabel labelMonitoring = new JLabel("Monitoring WiFi Publik");
        labelMonitoring.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMonitoring.setBounds(20, 60, 400, 20);
        panel.add(labelMonitoring);

        String[] columnNames = {"ID/MAC", "Nama Pengguna", "Kuota Sisa", "Status Jaringan", "Jam Akses"};
        Object[][] data = {}; 
        this.model = new DefaultTableModel(data, columnNames);
        this.table = new JTable(model);
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 580, 100);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane);

        JButton btnGrafik = new JButton("Tampilkan grafik");
        btnGrafik.setBounds(20, 295, 160, 25);
        btnGrafik.setBackground(Color.WHITE);
        panel.add(btnGrafik);

        JButton btnAnalisis = new JButton("Analisis Jam Sibuk");
        btnAnalisis.setBounds(20, 325, 160, 25);
        btnAnalisis.setBackground(Color.WHITE);
        panel.add(btnAnalisis);

        JTextField txtBuatCari = new JTextField("Cari nama pengguna...");
        txtBuatCari.setForeground(Color.GRAY);
        txtBuatCari.setBounds(20, 135, 150, 25);

        txtBuatCari.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
        public void focusGained(java.awt.event.FocusEvent e) {
        if (txtBuatCari.getText().equals("Cari nama pengguna...")) {
            txtBuatCari.setText("");
            txtBuatCari.setForeground(Color.BLACK); 
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent e) {
        if (txtBuatCari.getText().isEmpty()) {
            txtBuatCari.setText("Cari nama pengguna...");
            txtBuatCari.setForeground(Color.GRAY);
            }
        }
    });
    panel.add(txtBuatCari);

        return panel; 
    }

    public static void updateStatus(){
        
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GUI window = new GUI();
            window.setVisible(true);
        });
    }
}