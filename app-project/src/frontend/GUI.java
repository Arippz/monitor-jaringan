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
    private final String HALAMAN_UTAMA = "Monitoring Penggunaan WiFi Publik";
    private final String HALAMAN_TAMBAH = "Tambah Pengguna";
    private final String HALAMAN_GRAFIK = "Grafik";
    private final String HALAMAN_ANALISIS = "Analisis";

    public GUI() {
        System.out.print(DBWifi.databaseWifi.get(1));
        System.out.print(DBWifi.databaseWifi.get(3));
        setTitle("Kelompok 1");
        setSize(650, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(createManajemenPanel(), HALAMAN_UTAMA);
        mainContainer.add(createTambahPanel(), HALAMAN_TAMBAH);
        mainContainer.add(showGrafikPanel(), HALAMAN_GRAFIK);
        mainContainer.add(showAnalisisPanel(), HALAMAN_ANALISIS);
        
        cardLayout.show(mainContainer, HALAMAN_UTAMA);

        add(mainContainer);
        refreshTable();
    }

    public void clearTable() {
        if (this.model != null) {
            this.model.setRowCount(0);
        }
    }

    public void addRecord(Object[] recordData){
        if (this.model != null) {
            this.model.addRow(recordData);
        }
    }

    public void refreshTable() {
        clearTable();
        for (ArrayList<String> r : DBWifi.databaseWifi) {
            Object[] recordData = {
                r.get(0),
                r.get(1),
                r.get(2),
                r.get(3),
                r.get(4),
            };
            addRecord(recordData);
        }
    }



    private JPanel createManajemenPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel("Monitoring Penggunaan WiFi Publik");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBounds(20, 0, 500, 25);
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
        btnGrafik.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_GRAFIK));
        panel.add(btnGrafik);

        JButton btnAnalisis = new JButton("Analisis Jam Sibuk");
        btnAnalisis.setBounds(20, 325, 160, 25);
        btnAnalisis.setBackground(Color.WHITE);
        btnAnalisis.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_ANALISIS));
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

    private JPanel showGrafikPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Inner chart panel that draws a simple line chart from DBWifi data
        class ChartPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                java.util.List<ArrayList<String>> data = DBWifi.databaseWifi;
                if (data == null || data.isEmpty()) {
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString("Tidak ada data untuk ditampilkan", 20, 20);
                    return;
                }

                int w = getWidth();
                int h = getHeight();
                int marginLeft = 60;
                int marginRight = 150;
                int marginTop = 20;
                int marginBottom = 60;

                // Extract numeric quotas
                int n = data.size();
                int[] values = new int[n];
                int maxVal = 1;
                for (int i = 0; i < n; i++) {
                    String quota = data.get(i).get(2);
                    try {
                        int v = Integer.parseInt(quota.replaceAll("[^0-9]", ""));
                        values[i] = v;
                        if (v > maxVal) maxVal = v;
                    } catch (Exception ex) {
                        values[i] = 0;
                    }
                }

                int chartW = w - marginLeft - marginRight;
                int chartH = h - marginTop - marginBottom;

                // Draw axes
                g2.setColor(Color.BLACK);
                g2.drawLine(marginLeft, marginTop, marginLeft, marginTop + chartH); // Y axis
                g2.drawLine(marginLeft, marginTop + chartH, marginLeft + chartW, marginTop + chartH); // X axis

                // Y axis ticks and labels
                int ticks = 5;
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                for (int t = 0; t <= ticks; t++) {
                    int y = marginTop + (int) ((chartH * t) / (double) ticks);
                    int value = (int) Math.round(maxVal * (1 - t / (double) ticks));
                    g2.drawLine(marginLeft - 5, y, marginLeft, y);
                    g2.drawString(value + " MB", 5, y + 4);
                }

                // X axis points
                if (n > 1) {
                    int gap = chartW / (n - 1);
                    int[] xs = new int[n];
                    int[] ys = new int[n];
                    for (int i = 0; i < n; i++) {
                        xs[i] = marginLeft + i * gap;
                        double normalized = values[i] / (double) maxVal;
                        ys[i] = marginTop + (int) ((1 - normalized) * chartH);
                    }

                    // Draw area line chart
                    g2.setColor(new Color(30, 144, 255));
                    g2.setStroke(new BasicStroke(2f));

                    java.awt.geom.Path2D.Double areaPath = new java.awt.geom.Path2D.Double();
                    areaPath.moveTo(xs[0], marginTop + chartH);
                    areaPath.lineTo(xs[0], ys[0]);
                    for (int i = 1; i < n; i++) {
                        areaPath.lineTo(xs[i], ys[i]);
                    }
                    areaPath.lineTo(xs[n - 1], marginTop + chartH);
                    areaPath.closePath();

                    g2.setColor(new Color(30, 144, 255, 60));
                    g2.fill(areaPath);

                    java.awt.geom.Path2D.Double linePath = new java.awt.geom.Path2D.Double();
                    linePath.moveTo(xs[0], ys[0]);
                    for (int i = 1; i < n; i++) {
                        linePath.lineTo(xs[i], ys[i]);
                    }
                    g2.setColor(new Color(30, 144, 255));
                    g2.draw(linePath);

                    // Draw points and labels
                    g2.setColor(new Color(255, 69, 0));
                    for (int i = 0; i < n; i++) {
                        g2.fillOval(xs[i] - 4, ys[i] - 4, 8, 8);
                        String name = data.get(i).get(1);
                        String label = name.length() > 10 ? name.substring(0, 10) + ".." : name;
                        g2.setColor(Color.BLACK);
                        g2.drawString(label, xs[i] - 15, marginTop + chartH + 20);
                        g2.setColor(new Color(255, 69, 0));
                    }
                } else if (n == 1) {
                    // Single point
                    int x = marginLeft + chartW / 2;
                    double normalized = values[0] / (double) maxVal;
                    int y = marginTop + (int) ((1 - normalized) * chartH);
                    g2.setColor(new Color(255, 69, 0));
                    g2.fillOval(x - 4, y - 4, 8, 8);
                }
            }
        }

        ChartPanel chart = new ChartPanel();
        panel.add(chart, BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setBackground(Color.WHITE);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
            btnRefresh.setEnabled(false);
            Main backend = new Main();
            new Thread(() -> {
                // trigger backend update which modifies DBWifi and uses the GUI callbacks
                backend.Perubahan(GUI.this);
                // repaint chart on EDT and re-enable button
                SwingUtilities.invokeLater(() -> {
                    chart.repaint();
                    btnRefresh.setEnabled(true);
                });
            }).start();
        });
        top.add(btnRefresh);
        panel.add(top, BorderLayout.NORTH);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_UTAMA));
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        footer.add(btnKembali);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel showAnalisisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Analisis Jam Sibuk", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        class AnalysisChartPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                java.util.List<ArrayList<String>> data = DBWifi.databaseWifi;
                int width = getWidth();
                int height = getHeight();
                int marginLeft = 60;
                int marginRight = 20;
                int marginTop = 20;
                int marginBottom = 60;
                int chartW = width - marginLeft - marginRight;
                int chartH = height - marginTop - marginBottom;

                if (chartW <= 0 || chartH <= 0) {
                    return;
                }

                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, width, height);
                g2.setColor(Color.BLACK);
                g2.drawLine(marginLeft, marginTop, marginLeft, marginTop + chartH);
                g2.drawLine(marginLeft, marginTop + chartH, marginLeft + chartW, marginTop + chartH);

                int[] countByHour = new int[24];
                int maxHour = 0;
                
                // Gunakan histori jam akses yang telah terekam
                for (int hour = 0; hour < 24; hour++) {
                    countByHour[hour] = DBWifi.jamSibukHistory[hour];
                    if (countByHour[hour] > 0 && hour > maxHour) {
                        maxHour = hour;
                    }
                }
                
                // Tambahkan user saat ini per jam dari database
                for (ArrayList<String> row : data) {
                    if (row.size() > 4) {
                        try {
                            int hour = Integer.parseInt(row.get(4).split(":")[0]);
                            if (hour >= 0 && hour < 24) {
                                countByHour[hour]++;
                                if (hour > maxHour) {
                                    maxHour = hour;
                                }
                            }
                        } catch (Exception ex) {
                            // skip invalid time values
                        }
                    }
                }

                int maxCount = 1;
                for (int c : countByHour) {
                    if (c > maxCount) {
                        maxCount = c;
                    }
                }

                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                for (int i = 0; i <= 5; i++) {
                    int y = marginTop + (chartH * i) / 5;
                    int value = (int) Math.round(maxCount * (5 - i) / 5.0);
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.drawLine(marginLeft, y, marginLeft + chartW, y);
                    g2.setColor(Color.BLACK);
                    g2.drawString(value + " user", 5, y + 4);
                }

                for (int hour = 0; hour < 24; hour += 3) {
                    int x = marginLeft + (int) ((chartW * hour) / 23.0);
                    g2.setColor(Color.BLACK);
                    g2.drawLine(x, marginTop + chartH, x, marginTop + chartH + 5);
                    g2.drawString(String.format("%02d:00", hour), x - 15, marginTop + chartH + 20);
                }

                int[] xs = new int[24];
                int[] ys = new int[24];
                for (int hour = 0; hour < 24; hour++) {
                    xs[hour] = marginLeft + (int) ((chartW * hour) / 23.0);
                    double normalized = maxCount == 0 ? 0 : countByHour[hour] / (double) maxCount;
                    ys[hour] = marginTop + chartH - (int) (normalized * chartH);
                }

                java.awt.geom.Path2D.Double line = new java.awt.geom.Path2D.Double();
                line.moveTo(xs[0], ys[0]);
                for (int hour = 1; hour < 24; hour++) {
                    int cx = (xs[hour - 1] + xs[hour]) / 2;
                    int cy = (ys[hour - 1] + ys[hour]) / 2;
                    line.quadTo(xs[hour - 1], ys[hour - 1], cx, cy);
                }
                line.quadTo(xs[22], ys[22], xs[23], ys[23]);

                java.awt.geom.Path2D.Double area = new java.awt.geom.Path2D.Double(line);
                area.lineTo(xs[23], marginTop + chartH);
                area.lineTo(xs[0], marginTop + chartH);
                area.closePath();

                g2.setColor(new Color(30, 144, 255, 60));
                g2.fill(area);
                g2.setColor(new Color(30, 144, 255));
                g2.setStroke(new BasicStroke(2f));
                g2.draw(line);

                for (int hour = 0; hour < 24; hour++) {
                    if (countByHour[hour] > 0) {
                        if (hour == maxHour) {
                            g2.setColor(new Color(220, 20, 60));
                        } else {
                            g2.setColor(new Color(255, 140, 0));
                        }
                        g2.fillOval(xs[hour] - 4, ys[hour] - 4, 8, 8);
                        g2.setColor(Color.BLACK);
                        g2.drawString(String.valueOf(countByHour[hour]), xs[hour] - 6, ys[hour] - 10);
                    }
                }

                g2.setColor(Color.DARK_GRAY);
                g2.drawString("Jam terbaru: " + String.format("%02d:00", maxHour), marginLeft, marginTop - 5);
                g2.drawString("Total pengguna: " + data.size(), marginLeft + 160, marginTop - 5);
            }
        }

        AnalysisChartPanel chart = new AnalysisChartPanel();
        panel.add(chart, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> cardLayout.show(mainContainer, HALAMAN_UTAMA));
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        footer.add(btnKembali);
        bottom.add(footer, BorderLayout.SOUTH);

        panel.add(bottom, BorderLayout.SOUTH);
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
            DBWifi.addUser(mac, nama, jam);
            refreshTable();

            JOptionPane.showMessageDialog(
                this, 
                "Data '" + nama + "' berhasil ditambahkan!", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE
            );

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