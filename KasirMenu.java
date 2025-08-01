package com.mycompany.kasirakademik_noer_azis;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KasirMenu {

    private static UserAkademik userLogin;

    // Login user berdasarkan username dan password
    public static boolean loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userLogin = new UserAkademik(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("id")
                    );
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat login: " + e.getMessage());
        }
        return false;
    }

    // Register user baru
    public static boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        userLogin = new UserAkademik(username, password, userId);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat register: " + e.getMessage());
        }
        return false;
    }

    public static UserAkademik getUserLogin() {
        return userLogin;
    }

    public static void logout() {
        userLogin = null;
    }

    // Ambil semua data mahasiswa untuk admin
    public static List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> list = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("nim"),
                        rs.getInt("user_id")
                );
                list.add(mhs);
            }
        } catch (SQLException e) {
            System.out.println("Error mengambil data mahasiswa: " + e.getMessage());
        }

        return list;
    }

    // Ambil semua data transaksi
    public static List<Transaksi> getAllTransaksi() {
        List<Transaksi> list = new ArrayList<>();
        String query = "SELECT * FROM transaksi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int tagihanId = rs.getInt("tagihan_id");
                double jumlahBayar = rs.getDouble("jumlah_bayar");
                Date tanggalBayarDate = rs.getDate("tanggal_bayar");
                LocalDate tanggalBayar = tanggalBayarDate != null ? tanggalBayarDate.toLocalDate() : null;

                Transaksi transaksi = new Transaksi(id, tagihanId, (int) jumlahBayar, tanggalBayar);
                list.add(transaksi);
            }
        } catch (SQLException e) {
            System.out.println("Error mengambil data transaksi: " + e.getMessage());
        }

        return list;
    }

    // Ambil daftar tagihan mahasiswa berdasarkan user login
    public static List<Tagihan> getTagihanUser() {
        List<Tagihan> list = new ArrayList<>();
        if (userLogin == null) return list;

        int mahasiswaId = getMahasiswaIdByUserId(userLogin.getId());
        if (mahasiswaId == -1) {
            System.out.println("Mahasiswa tidak ditemukan untuk user_id: " + userLogin.getId());
            return list;
        }

        return getTagihanByMahasiswaId(mahasiswaId);
    }

    // Ambil tagihan berdasarkan mahasiswa_id
    public static List<Tagihan> getTagihanByMahasiswaId(int mahasiswaId) {
        List<Tagihan> list = new ArrayList<>();
        if (mahasiswaId == -1) return list;

        String query = "SELECT * FROM tagihan WHERE mahasiswa_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, mahasiswaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int mhsId = rs.getInt("mahasiswa_id");
                    String keterangan = rs.getString("keterangan");
                    int jumlah = rs.getInt("total");

                    String lunasStr = rs.getString("lunas");
                    String status = "Belum Lunas";
                    if (lunasStr != null && (lunasStr.equalsIgnoreCase("s")
                            || lunasStr.equalsIgnoreCase("y")
                            || lunasStr.equals("1"))) {
                        status = "Lunas";
                    }

                    Tagihan tagihan = new Tagihan(id, mhsId, keterangan, jumlah, status);
                    list.add(tagihan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error mengambil tagihan: " + e.getMessage());
        }
        return list;
    }

    // Ambil mahasiswa_id berdasarkan user_id
    private static int getMahasiswaIdByUserId(int userId) {
        String query = "SELECT id FROM mahasiswa WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error mendapatkan mahasiswa_id: " + e.getMessage());
        }
        return -1;
    }

    // Proses pembayaran tagihan dan catat transaksi
    public static void bayarTagihan(Tagihan tagihan, double jumlahBayar) {
        if (userLogin == null) return;

        String updateTagihan = "UPDATE tagihan SET lunas = 1 WHERE id = ?";
        String insertTransaksi = "INSERT INTO transaksi (tagihan_id, jumlah_bayar, tanggal_bayar) VALUES (?, ?, CURRENT_DATE())";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUpdate = conn.prepareStatement(updateTagihan);
                 PreparedStatement stmtInsert = conn.prepareStatement(insertTransaksi)) {

                // Update status lunas
                stmtUpdate.setInt(1, tagihan.getId());
                stmtUpdate.executeUpdate();

                // Insert ke tabel transaksi
                stmtInsert.setInt(1, tagihan.getId());
                stmtInsert.setDouble(2, jumlahBayar);
                stmtInsert.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaksi gagal, rollback: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error saat membayar tagihan: " + e.getMessage());
        }
    }
}
