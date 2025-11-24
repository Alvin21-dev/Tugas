import java.io.*;
import java.util.*;

abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public abstract void tampilMenu();
}

class Makanan extends MenuItem {
    public Makanan(String nama, double harga, String kategori) {
        super(nama, harga, kategori);
    }

    @Override
    public void tampilMenu() {
        System.out.println(getNama() + " | Harga: " + getHarga() + " | Kategori: " + getKategori());
    }
}

class Minuman extends MenuItem {
    public Minuman(String nama, double harga, String kategori) {
        super(nama, harga, kategori);
    }

    @Override
    public void tampilMenu() {
        System.out.println(getNama() + " | Harga: " + getHarga() + " | Kategori: " + getKategori());
    }
}

class Diskon extends MenuItem {
    private double diskon; // Persentase diskon, misalnya 0.1 untuk 10%

    public Diskon(String nama, double harga, String kategori, double diskon) {
        super(nama, harga, kategori);
        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Promo: " + getNama() + " | Harga: " + getHarga() + " | Kategori: " + getKategori()
                + " | Diskon: " + (diskon * 100) + "%");
    }
}

class Menu {
    private ArrayList<MenuItem> daftarMenu;

    public Menu() {
        daftarMenu = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public void tampilMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }
        System.out.println("==== Menu Restoran ====");
        daftarMenu.sort(Comparator.comparing(MenuItem::getKategori));
        for (MenuItem item : daftarMenu) {
            item.tampilMenu();
        }
    }

    public MenuItem getItemByNama(String nama) throws ItemNotFoundException {
        for (MenuItem item : daftarMenu) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item dengan nama '" + nama + "' tidak ditemukan.");
    }

    public ArrayList<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public void simpanKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.write("Makanan," + m.getNama() + "," + m.getHarga() + "," + m.getKategori());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.write("Minuman," + m.getNama() + "," + m.getHarga() + "," + m.getKategori());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.write(
                            "Diskon," + d.getNama() + "," + d.getHarga() + "," + d.getKategori() + "," + d.getDiskon());
                }
                writer.newLine();
            }
            System.out.println("Menu berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Error menyimpan menu: " + e.getMessage());
        }
    }

    public void muatDariFile(String namaFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String tipe = parts[0];
                String nama = parts[1];
                double harga = Double.parseDouble(parts[2]);
                if (tipe.equals("Makanan")) {
                    String kategori = parts[3];
                    tambahItem(new Makanan(nama, harga, kategori));
                } else if (tipe.equals("Minuman")) {
                    String kategori = parts[3];
                    tambahItem(new Minuman(nama, harga, kategori));
                } else if (tipe.equals("Diskon")) {
                    String kategori = parts[3];
                    double diskon = Double.parseDouble(parts[4]);
                    tambahItem(new Diskon(nama, harga, kategori, diskon));
                }
            }
            System.out.println("Menu berhasil dimuat dari file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Error memuat menu: " + e.getMessage());
        }
    }
}

class Pesanan {
    private ArrayList<MenuItem> daftarPesanan;

    public Pesanan() {
        daftarPesanan = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        daftarPesanan.add(item);
    }

    public double hitungTotal() {
        double total = 0;
        double diskonTotal = 0;
        for (MenuItem item : daftarPesanan) {
            total += item.getHarga();
            if (item instanceof Diskon) {
                diskonTotal += ((Diskon) item).getDiskon() * total;
            }
        }
        return total - diskonTotal;
    }

    public void tampilStruk() {
        System.out.println("=== Struk Pesanan ===");
        for (MenuItem item : daftarPesanan) {
            item.tampilMenu();
        }
        System.out.println("Total: " + hitungTotal());
    }

    public void simpanStrukKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write("=== Struk Pesanan ===\n");
            for (MenuItem item : daftarPesanan) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.write("Makanan: " + m.getNama() + " | " + m.getHarga() + "\n");
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.write("Minuman: " + m.getNama() + " | " + m.getHarga() + "\n");
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.write("Diskon: " + d.getNama() + " | " + d.getHarga() + " | " + (d.getDiskon() * 100) + "%\n");
                }
            }
            writer.write("Total: " + hitungTotal() + "\n");
            System.out.println("Struk berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Error menyimpan struk: " + e.getMessage());
        }
    }

    public void muatStrukDariFile(String namaFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Makanan:")) {
                    System.out.println("Memuat struk dari file...");
                }
            }
        } catch (IOException e) {
            System.out.println("Error memuat struk: " + e.getMessage());
        }
    }
}

class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

public class Tugas3PemogramanDekstop {
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);
    private static int strukCounter = 1;

    public static void main(String[] args) {
        menu.muatDariFile("menu.txt");

        boolean running = true;
        while (running) {
            System.out.println("\n=== Menu Utama Restoran ===");
            System.out.println("1. Tambah Item Menu");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Terima Pesanan");
            System.out.println("4. Simpan Menu ke File");
            System.out.println("5. Lihat Struk Pesanan");
            System.out.println("6. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahItemMenu();
                    break;
                case 2:
                    menu.tampilMenu();
                    break;
                case 3:
                    terimaPesanan();
                    break;
                case 4:
                    menu.simpanKeFile("menu.txt");
                    break;
                case 5:
                    lihatStrukPesanan();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }

    private static void tambahItemMenu() {
        System.out.println("Pilih jenis item: 1. Makanan 2. Minuman 3. Diskon");
        int jenis = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        if (jenis == 1) {
            String kategori = "Makanan";
            menu.tambahItem(new Makanan(nama, harga, kategori));
        } else if (jenis == 2) {
            String kategori = "Minuman";
            menu.tambahItem(new Minuman(nama, harga, kategori));
        } else if (jenis == 3) {
            System.out.print("Kategori: ");
            String kategori = scanner.nextLine();
            System.out.print("Diskon (dalam desimal, e.g., 0.1 untuk 10%): ");
            double diskon = scanner.nextDouble();
            menu.tambahItem(new Diskon(nama, harga, kategori, diskon));
        } else {
            System.out.println("Jenis tidak valid.");
        }
    }

    private static void terimaPesanan() {
        Pesanan pesanan = new Pesanan();
        boolean pesanLagi = true;
        while (pesanLagi) {
            System.out.print("Masukkan nama item (atau 'selesai' untuk selesai): ");
            String namaItem = scanner.nextLine();
            if (namaItem.equalsIgnoreCase("selesai")) {
                pesanLagi = false;
            } else {
                try {
                    MenuItem item = menu.getItemByNama(namaItem);
                    pesanan.tambahItem(item);
                    System.out.println("Item ditambahkan ke pesanan.");
                } catch (ItemNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        pesanan.tampilStruk();
        System.out.print("Simpan struk ke file? (y/n): ");
        String simpan = scanner.nextLine();
        if (simpan.equalsIgnoreCase("y")) {
            String namaFile = "struk" + strukCounter + ".txt";
            pesanan.simpanStrukKeFile(namaFile);
            System.out.println("Struk disimpan dengan nama file: " + namaFile);
            strukCounter++;
        }
    }

    private static void lihatStrukPesanan() {
        File currentDir = new File(".");
        File[] strukFiles = currentDir.listFiles((dir, name) -> name.matches("struk\\d+\\.txt"));
        if (strukFiles == null || strukFiles.length == 0) {
            System.out.println("Tidak ada file struk yang ditemukan.");
            return;
        }
        System.out.println("Daftar file struk:");
        for (int i = 0; i < strukFiles.length; i++) {
            System.out.println((i + 1) + ". " + strukFiles[i].getName());
        }
        System.out.print("Pilih nomor file struk yang ingin dilihat: ");
        try {
            int pilihanFile = Integer.parseInt(scanner.nextLine());
            if (pilihanFile < 1 || pilihanFile > strukFiles.length) {
                System.out.println("Pilihan tidak valid.");
                return;
            }
            File selectedFile = strukFiles[pilihanFile - 1];
            System.out.println("Isi file " + selectedFile.getName() + ":");
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error membaca file: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
        }
    }
}
