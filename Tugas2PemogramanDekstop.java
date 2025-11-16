import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    String nama;
    int harga;
    String kategori;
    String urut;

    Menu(String urut, String nama, int harga, String kategori) {
        this.urut = urut;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Tugas2PemogramanDekstop {
    static ArrayList<Menu> menu = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static {
        // Inisialisasi menu awal
        menu.add(new Menu("1, ", "Sate Padang", 15000, "makanan"));
        menu.add(new Menu("2, ", "Mie Ayam", 12000, "makanan"));
        menu.add(new Menu("3, ", "Bakso", 13000, "makanan"));
        menu.add(new Menu("4, ", "Nasi Goreng", 17000, "makanan"));
        menu.add(new Menu("1, ", "Teh Obeng", 5000, "minuman"));
        menu.add(new Menu("2, ", "Kopi Susu", 10000, "minuman"));
        menu.add(new Menu("3, ", "Milo Es", 7000, "minuman"));
        menu.add(new Menu("4, ", "Bandrek", 8000, "minuman"));
    }

    public static void main(String[] args) throws java.io.IOException {
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        while (true) {
            System.out.println("\n--- Menu Utama ---");
            System.out.println("1. Pesan makanan/minuman");
            System.out.println("2. Manajemen menu restoran (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            String pilihan = input.readLine();

            switch (pilihan) {
                case "1" -> Menus();
                case "2" -> manajemenMenu();
                case "3" -> {
                    System.out.println("Terima kasih, program selesai.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void Menus() throws java.io.IOException {
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        // Tampilkan menu makanan dan minuman
        System.out.println("[Makanan]");
        for (Menu m : menu) {
            if ("makanan".equals(m.kategori)) {
                System.out.println(m.urut + m.nama + " - Rp " + m.harga);
            }
        }

        System.out.println("[Minuman]");
        for (Menu m : menu) {
            if ("minuman".equals(m.kategori)) {
                System.out.println(m.urut + m.nama + " - Rp " + m.harga);
            }
        }

        System.out.println("[Ketik 'menu = jumlah' & ketik 'selesai' untuk berhenti]");

        ArrayList<String> pesanan = new ArrayList<>();
        ArrayList<Integer> jumlah = new ArrayList<>();

        // Input pesanan dari pengguna
        while (true) {
            System.out.print("Pesanan: ");
            String line = input.readLine();
            if (line.equalsIgnoreCase("selesai")) break;

            String[] parts = line.split("=");
            if (parts.length != 2) {
                System.out.println("Format salah, coba lagi.");
                continue;
            }

            String namaPesanan = parts[0].trim();
            int qty;
            try {
                qty = Integer.parseInt(parts[1].trim());
            } catch (Exception e) {
                System.out.println("Jumlah harus angka, coba lagi.");
                continue;
            }

            boolean ada = false;
            for (Menu m : menu) {
                if (m.nama.equalsIgnoreCase(namaPesanan)) {
                    pesanan.add(m.nama);
                    jumlah.add(qty);
                    ada = true;
                    break;
                }
            }
            if (!ada) {
                System.out.println("Menu tidak ditemukan, coba lagi.");
            }
        }

        // Hitung total akhir dan diskon
        int total = 0;
        double diskonMinuman = 0;
        for (int i = 0; i < pesanan.size(); i++) {
            Menu m = null;
            for (Menu temp : menu) {
                if (temp.nama.equalsIgnoreCase(pesanan.get(i))) {
                    m = temp;
                    break;
                }
            }
            total += m.harga * jumlah.get(i);
            if ("minuman".equals(m.kategori) && jumlah.get(i) >= 2) {
                int gratis = jumlah.get(i) / 2;
                diskonMinuman += gratis * m.harga;
            }
        }

        double diskon = total > 100000 ? total * 0.1 : 0;
        if (total <= 50000) diskonMinuman = 0; // Diskon minuman hanya jika total > 50000
        double bayarSetelahDiskon = total - diskon - diskonMinuman;
        double pajak = bayarSetelahDiskon * 0.1;
        int biayaPelayanan = 20000;
        double totalBayar = bayarSetelahDiskon + pajak + biayaPelayanan;

        // Cetak struk pesanan
        System.out.println("\n=== STRUK PEMESANAN ===");
        for (int i = 0; i < pesanan.size(); i++) {
            Menu m = null;
            for (Menu temp : menu) {
                if (temp.nama.equalsIgnoreCase(pesanan.get(i))) {
                    m = temp;
                    break;
                }
            }
            int subtotal = m.harga * jumlah.get(i);
            System.out.println(pesanan.get(i) + " x " + jumlah.get(i) + " = Rp " + subtotal);
        }
        System.out.println("Total: Rp " + total);
        if (diskon > 0) System.out.println("Diskon 10%: -Rp " + (int) diskon);
        if (diskonMinuman > 0) System.out.println("Diskon beli satu gratis satu minuman: -Rp " + (int) diskonMinuman);
        System.out.println("Pajak 10%: Rp " + (int) pajak);
        System.out.println("Biaya Pelayanan: Rp " + biayaPelayanan);
        System.out.println("Total Bayar: Rp " + (int) totalBayar);
    }

    static void manajemenMenu() throws java.io.IOException {
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        while (true) {
            System.out.println("\n--- Manajemen Menu Restoran ---");
            System.out.println("1. Tambah menu");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("4. Kembali ke menu utama");
            System.out.print("Pilih menu: ");
            String pilihan = input.readLine();

            switch (pilihan) {
                case "1" -> tambahMenu();
                case "2" -> ubahHargaMenu();
                case "3" -> hapusMenu();
                case "4" -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void tambahMenu() {
        System.out.print("Masukkan nama menu baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga: ");
        int harga = inputIntMenu(1, Integer.MAX_VALUE);
        String kategori;
        while (true) {
            System.out.print("Masukkan kategori (makanan/minuman): ");
            kategori = scanner.nextLine().toLowerCase();
            if (kategori.equals("makanan") || kategori.equals("minuman")) break;
            System.out.println("Kategori tidak valid, coba lagi.");
        }
        // Hitung urut berdasarkan kategori
        int count = 1;
        for (Menu m : menu) {
            if (m.kategori.equals(kategori)) count++;
        }
        String urut = count + ", ";
        menu.add(new Menu(urut, nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    static void ubahHargaMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu yang akan diubah harga: ");
        int nomor = inputIntMenu(1, menu.size());
        Menu menuPilih = menu.get(nomor - 1);
        System.out.print("Apakah Anda yakin ingin mengubah harga " + menuPilih.nama + "? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            System.out.print("Masukkan harga baru: ");
            int hargaBaru = inputIntMenu(1, Integer.MAX_VALUE);
            menuPilih.harga = hargaBaru;
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    static void hapusMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu yang akan dihapus: ");
        int nomor = inputIntMenu(1, menu.size());
        Menu menuPilih = menu.get(nomor - 1);
        System.out.print("Apakah Anda yakin ingin menghapus " + menuPilih.nama + "? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menu.remove(nomor - 1);
            // Update urut setelah hapus
            updateUrut();
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    static void tampilkanMenu() {
        System.out.println("Menu Makanan:");
        for (Menu m : menu) {
            if (m.kategori.equalsIgnoreCase("makanan")) {
                System.out.println("- " + m.nama + " : Rp " + m.harga);
            }
        }
        System.out.println("Menu Minuman:");
        for (Menu m : menu) {
            if (m.kategori.equalsIgnoreCase("minuman")) {
                System.out.println("- " + m.nama + " : Rp " + m.harga);
            }
        }
    }

    static void tampilkanMenuDenganNomor() {
        int no = 1;
        for (Menu m : menu) {
            System.out.println(no + ". " + m.nama + " (Rp " + m.harga + ") - " + m.kategori);
            no++;
        }
    }

    static void updateUrut() {
        int makananCount = 1;
        int minumanCount = 1;
        for (Menu m : menu) {
            if (m.kategori.equals("makanan")) {
                m.urut = makananCount + ", ";
                makananCount++;
            } else if (m.kategori.equals("minuman")) {
                m.urut = minumanCount + ", ";
                minumanCount++;
            }
        }
    }

    static int inputIntMenu(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Input tidak valid, masukkan angka antara " + min + " sampai " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid, masukkan angka: ");
            }
        }
    }
}
