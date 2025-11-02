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

public class Tugas1PemogramanDekstop {
    static Menu[] menu = {
        new Menu("1, ","Sate Padang", 15000, "makanan"),
        new Menu("2, ","Mie Ayam", 12000, "makanan"),
        new Menu("3, ","Bakso", 13000, "makanan"),
        new Menu("4, ","Nasi Goreng", 17000, "makanan"),
        new Menu("1, ","Teh Obeng", 5000, "minuman"),
        new Menu("2, ","Kopi Susu", 10000, "minuman"),
        new Menu("3, ","Milo Es", 7000, "minuman"),
        new Menu("4, ","Bandrek", 8000, "minuman")
    };

    public static void main(String[] args) throws java.io.IOException {
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

        // Tampilkan menu makanan dan minuman
        System.out.println("[Makanan]");
        for (Menu m : menu) if ("makanan".equals(m.kategori)) 
            System.out.println(m.urut + m.nama + " - Rp " + m.harga);

        System.out.println("[Minuman]");
        for (Menu m : menu) if ("minuman".equals(m.kategori)) 
            System.out.println(m.urut + m.nama + " - Rp " + m.harga);

        System.out.println("[Ketik 'menu = jumlah' & ketik 'selesai' untuk berhenti]");

        String[] pesanan = new String[4]; 
        int[] jumlah = new int[4];

        // Input pesanan dari pengguna
        for (int i = 0; i < 4; i++) {
            System.out.print("Pesanan ke-" + (i + 1) + ": ");
            String line = input.readLine();
            if (line.equalsIgnoreCase("selesai")) break;

            String[] parts = line.split("=");
            if (parts.length != 2) {
                System.out.println("Format salah, coba lagi.");
                i--;
                continue;
            }

            String namaPesanan = parts[0].trim();
            int qty;
            try {
                qty = Integer.parseInt(parts[1].trim());
            } catch (Exception e) {
                System.out.println("Jumlah harus angka, coba lagi.");
                i--;
                continue;
            }

            boolean ada = false;
            for (Menu m : menu) {
                if (m.nama.equalsIgnoreCase(namaPesanan)) {
                    pesanan[i] = m.nama;
                    jumlah[i] = qty;
                    ada = true;
                    break;
                }
            }
            if (!ada) {
                System.out.println("Menu tidak ditemukan, coba lagi.");
                i--;
            }
        }

        // Hitung total akhir dan diskon
        int total = 0;
        int hargaMinumanDiskon = 0;
        for (int i = 0; i < pesanan.length && pesanan[i] != null; i++) {
            Menu m = null;
            for (Menu temp : menu)
                if (temp.nama.equalsIgnoreCase(pesanan[i])) m = temp;

            total += m.harga * jumlah[i];
            if ("minuman".equals(m.kategori) && jumlah[i] >= 2) {
                hargaMinumanDiskon = m.harga;
            }
        }

        double diskon = total > 100000 ? total * 0.1 : 0;
        double diskonMinuman = total > 50000 ? hargaMinumanDiskon : 0;
        double bayarSetelahDiskon = total - diskon - diskonMinuman;
        double pajak = bayarSetelahDiskon * 0.1;
        int biayaPelayanan = 20000;
        double totalBayar = bayarSetelahDiskon + pajak + biayaPelayanan;

        // Cetak struk pesanan
        System.out.println("\n=== STRUK PEMESANAN ===");
        for (int i = 0; i < pesanan.length && pesanan[i] != null; i++) {
            Menu m = null;
            for (Menu temp : menu)
                if (temp.nama.equalsIgnoreCase(pesanan[i])) m = temp;

            int subtotal = m.harga * jumlah[i];
            System.out.println(pesanan[i] + " x " + jumlah[i] + " = Rp " + subtotal);
        }
        System.out.println("Total: Rp " + total);
        if (diskon > 0) System.out.println("Diskon 10%: -Rp " + (int)diskon);
        if (diskonMinuman > 0) System.out.println("[Diskon beli satu gratis satu minuman:] -Rp " + (int)diskonMinuman);
        System.out.println("Pajak 10%: Rp " + (int)pajak);
        System.out.println("Biaya Pelayanan: Rp " + biayaPelayanan);
        System.out.println("Total Bayar: Rp " + (int)totalBayar);
    }
}