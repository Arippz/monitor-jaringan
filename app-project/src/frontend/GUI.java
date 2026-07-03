package frontend;

import backend.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import backend.DBWifi; 

import java.util.ArrayList;
import java.awt.*;

public class GUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private DefaultTableModel tableModel;
    private JTable dataTable;

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
        mainContainer.add(createTambahPanel(), HALAMAN_TAMBAH); // <--- Ditambahkan disini
        
        // Populate table dengan data awal
        refreshTable();
        
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
        tableModel = new DefaultTableModel(data, columnNames);
        dataTable = new JTable(tableModel);
        
        dataTable.setShowGrid(false);
        dataTable.setIntercellSpacing(new Dimension(0, 5)); 
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
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


   private JPanel createTambahPanel() {
    JPanel panel = new JPanel(null);
    panel.setBackground(Color.WHITE);

    JLabel labelTitle = new JLabel("Tambah Pengguna");
    labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
    labelTitle.setBounds(40, 40, 250, 25);
    panel.add(labelTitle);


    JLabel labelMac = new JLabel("ID / MAC Address");
    labelMac.setFont(new Font("Arial", Font.PLAIN, 13));
    labelMac.setBounds(40, 90, 120, 25);
    panel.add(labelMac);

    JTextField txtMac = new JTextField("Contoh: 1A:2B:3C:4D");
    txtMac.setForeground(Color.LIGHT_GRAY); // Warna teks placeholder
    txtMac.setBounds(180, 90, 200, 25);
    
    txtMac.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (txtMac.getText().equals("Contoh: 1A:2B:3C:4D")) {
                txtMac.setText(""); 
                txtMac.setForeground(Color.BLACK);
            }
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (txtMac.getText().isEmpty()) {
                txtMac.setText("Contoh: 1A:2B:3C:4D");
                txtMac.setForeground(Color.LIGHT_GRAY);
            }
        }
    });
    panel.add(txtMac);

    JLabel labelNama = new JLabel("Nama Pengguna");
    labelNama.setFont(new Font("Arial", Font.PLAIN, 13));
    labelNama.setBounds(40, 125, 120, 25);
    panel.add(labelNama);

    JTextField txtNama = new JTextField("Masukkan nama...");
    txtNama.setForeground(Color.LIGHT_GRAY);
    txtNama.setBounds(180, 125, 200, 25);
    
    txtNama.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (txtNama.getText().equals("Masukkan nama...")) {
                txtNama.setText("");
                txtNama.setForeground(Color.BLACK);
            }
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (txtNama.getText().isEmpty()) {
                txtNama.setText("Masukkan nama...");
                txtNama.setForeground(Color.LIGHT_GRAY);
            }
        }
    });
    panel.add(txtNama);

    JLabel labelJam = new JLabel("Jam Akses (HH:00)");
    labelJam.setFont(new Font("Arial", Font.PLAIN, 13));
    labelJam.setBounds(40, 160, 120, 25);
    panel.add(labelJam);

    JTextField txtJam = new JTextField("Contoh: 14:00");
    txtJam.setForeground(Color.LIGHT_GRAY);
    txtJam.setBounds(180, 160, 200, 25);
    
    txtJam.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (txtJam.getText().equals("Contoh: 14:00")) {
                txtJam.setText("");
                txtJam.setForeground(Color.BLACK);
            }
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (txtJam.getText().isEmpty()) {
                txtJam.setText("Contoh: 14:00");
                txtJam.setForeground(Color.LIGHT_GRAY);
            }
        }
    });
    panel.add(txtJam);
    JButton btnTambah = new JButton("+ Tambah");
    btnTambah.setBounds(40, 205, 150, 30);
    btnTambah.setBackground(Color.WHITE);
    panel.add(btnTambah);

    JButton btnBatal = new JButton("Batal");
    btnBatal.setBounds(200, 205, 80, 30);
    btnBatal.setBackground(Color.WHITE);
    panel.add(btnBatal);


btnTambah.addActionListener(e -> {
    String mac = txtMac.getText().trim();
    String nama = txtNama.getText().trim();
    String jam = txtJam.getText().trim();

    boolean isMacEmpty = mac.isEmpty() || mac.equals("Contoh: 1A:2B:3C:4D");
    boolean isNamaEmpty = nama.isEmpty() || nama.equals("Masukkan nama...");
    boolean isJamEmpty = jam.isEmpty() || jam.equals("Contoh: 14:00");

    if (isMacEmpty || isNamaEmpty || isJamEmpty) {
        JOptionPane.showMessageDialog(
            this, 
            "Semua data harus diisi! Tidak boleh ada kolom yang kosong.", 
            "Peringatan", 
            JOptionPane.WARNING_MESSAGE
        );
    } 
    else {
        // Tambahkan data ke database
        DBWifi.addUser(mac, nama, jam);
        
        // Refresh tabel
        refreshTable();
        
        JOptionPane.showMessageDialog(
            this, 
            "Data '" + nama + "' berhasil ditambahkan!", 
            "Sukses", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Clear input fields
        txtMac.setText("Contoh: 1A:2B:3C:4D");
        txtMac.setForeground(Color.LIGHT_GRAY);
        txtNama.setText("Masukkan nama...");
        txtNama.setForeground(Color.LIGHT_GRAY);
        txtJam.setText("Contoh: 14:00");
        txtJam.setForeground(Color.LIGHT_GRAY);
        
        cardLayout.show(mainContainer, HALAMAN_UTAMA);
    }
});

    btnBatal.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_UTAMA));

    return panel;
}

    // Metode untuk refresh tabel dengan data terbaru dari database
    private void refreshTable() {
        // Hapus semua baris di tabel
        tableModel.setRowCount(0);
        
        // Tambahkan data dari database ke tabel
        for (ArrayList<String> row : DBWifi.databaseWifi) {
            tableModel.addRow(row.toArray());
        }
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