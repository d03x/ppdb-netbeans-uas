/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbtapp.component;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import cbtapp.utils.DB;
public class EditSiswaDialog extends JDialog {
    private boolean updated = false;
    private String nisn;

    private JTextField tfNama, tfTempatLahir, tfTanggalLahir, tfNoHp, tfNoWa, tfAlamat;
    private JLabel fotoLabel;
    private File selectedFoto;

    public EditSiswaDialog(Frame parent, boolean modal, String nisn) {
        super(parent, modal);
        this.nisn = nisn;
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Buat field, button, layout, dll.
        // Tambahkan tombol "Pilih Foto" dan simpan file di selectedFoto

        JButton pilihFotoBtn = new JButton("Pilih Foto");
        pilihFotoBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser("uploads/");
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFoto = fc.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFoto.getAbsolutePath());
                fotoLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH)));
            }
        });

        JButton simpanBtn = new JButton("Simpan");
        simpanBtn.addActionListener(e -> simpanData());
        // layout field dan tombol ke panel...
    }

    private void loadData() {
        try {
            Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM peserta WHERE nisn = ?");
            ps.setString(1, nisn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tfNama.setText(rs.getString("nama_lengkap"));
                // Set field lain...
                String fotoNama = rs.getString("foto");
                if (fotoNama != null) {
                    File f = new File("uploads/" + fotoNama);
                    if (f.exists()) {
                        fotoLabel.setIcon(new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH)));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
        }
    }

    private void simpanData() {
        try {
            Connection conn = DB.getConnection();

            // Jika ada foto baru dipilih
            String fotoNama = null;
            if (selectedFoto != null) {
                fotoNama = nisn + "_" + selectedFoto.getName();
                File target = new File("uploads/" + fotoNama);
                Files.copy(selectedFoto.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            String sql = "UPDATE peserta SET nama_lengkap=?, tempat_lahir=?, tanggal_lahir=?, no_hp=?, no_wa=?, alamat=?"
                       + (fotoNama != null ? ", foto=?" : "") + " WHERE nisn=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tfNama.getText());
            ps.setString(2, tfTempatLahir.getText());
            ps.setString(3, tfTanggalLahir.getText());
            ps.setString(4, tfNoHp.getText());
            ps.setString(5, tfNoWa.getText());
            ps.setString(6, tfAlamat.getText());

            if (fotoNama != null) {
                ps.setString(7, fotoNama);
                ps.setString(8, nisn);
            } else {
                ps.setString(7, nisn);
            }

            ps.executeUpdate();
            updated = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }
    }

    public boolean isUpdated() {
        return updated;
    }
}
