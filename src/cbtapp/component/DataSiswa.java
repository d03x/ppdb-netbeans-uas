/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cbtapp.component;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Image;

/**
 *
 * @author d03x
 */
public class DataSiswa extends javax.swing.JPanel {

    /**
     * Creates new form DataSiswa
     */
    public DataSiswa() {
        initComponents();
        this.loadData();
        addTableClickListener(); // Add the table click listener

    }

 

    private void showStudentDetails(int row) {
        String nisn = jTable1.getValueAt(row, 1).toString();
        String nama = jTable1.getValueAt(row, 2).toString();
        String tanggalLahir = jTable1.getValueAt(row, 3).toString();
        String tempatLahir = jTable1.getValueAt(row, 4).toString();
        String noHp = jTable1.getValueAt(row, 5).toString();
        String noWa = jTable1.getValueAt(row, 6).toString();
        String alamat = jTable1.getValueAt(row, 7).toString();
        String namaSekolah = jTable1.getValueAt(row, 8).toString();

        // Ambil nama file foto dari database
        String namaFileFoto = null;
        try {
            java.sql.Connection conn = cbtapp.utils.DB.getConnection();
            String sql = "SELECT foto FROM peserta WHERE nisn = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nisn);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                namaFileFoto = rs.getString("foto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil foto: " + e.getMessage());
        }

        String detailText = String.format("""
        NISN: %s
        Nama: %s
        Tanggal Lahir: %s
        Tempat Lahir: %s
        Nomor HP: %s
        Nomor Whatsapp: %s
        Alamat: %s
        Asal Sekolah: %s
        """, nisn, nama, tanggalLahir, tempatLahir, noHp, noWa, alamat, namaSekolah);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JTextArea textArea = new JTextArea(detailText);
        textArea.setEditable(false);
        textArea.setOpaque(false);

        // Siapkan gambar
        JLabel imageLabel;
        if (namaFileFoto != null && !namaFileFoto.isEmpty()) {
            String imagePath = "uploads/" + namaFileFoto;
            File file = new File(imagePath);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaledImage));
            } else {
                imageLabel = new JLabel("Foto tidak ditemukan");
            }
        } else {
            imageLabel = new JLabel("Tidak ada foto");
        }

        panel.add(imageLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(textArea);

        JOptionPane.showMessageDialog(this, panel, "Detail Siswa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addTableClickListener() {
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row != -1 && evt.getClickCount() == 2) {
                    showStudentDetails(row);
                }
            }
        });
    }

    //edit
    public void loadData() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear table dulu

        String sql = """
    SELECT 
        peserta.nisn,peserta.foto, peserta.nama_lengkap, peserta.tanggal_lahir, peserta.tempat_lahir,
        peserta.no_hp, peserta.no_wa, peserta.alamat, sekolah_asal.nama AS nama_sekolah
    FROM peserta
    LEFT JOIN sekolah_asal ON peserta.npsn_sekolah_asal = sekolah_asal.npsn
    ORDER BY peserta.nama_lengkap
""";

        try {
            java.sql.Connection conn = cbtapp.utils.DB.getConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            int id = 0;
            while (rs.next()) {
                id++;
                Object[] row = new Object[]{
                    id,
                    rs.getString("nisn"),
                    rs.getString("nama_lengkap"),
                    rs.getDate("tanggal_lahir"),
                    rs.getString("tempat_lahir"),
                    rs.getString("no_hp"),
                    rs.getString("no_wa"),
                    rs.getString("alamat"),
                    rs.getString("nama_sekolah"),
                    rs.getString("foto")

                };
                model.addRow(row);
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal load data siswa: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nisn", "Nama", "Tanggal Lahir", "Tempat Lahir", "Nomor HP", "Nomor Whatsapp", "Alamat", "Asal Sekolah", "foto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Tambah Data");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.loadData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
    }//GEN-LAST:event_jButton1MouseClicked
    private cbtapp.component.Pendaftaran pendaftaran3;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
