public import java.util.Arrays;
import java.util.LinkedList;

public class Tugas1StrukturData {
    public static void main(String[] args) {
        //1. Deklarasi variabel integer bernama StrukturBaris

        int StrukturBaris = 3;
        System.out.println("StrukturBaris: " + StrukturBaris);

        // 2. Deklarasi variabel string bernama KataBaru berisi "Deklarasi tipe data String"

        String KataBaru = "Deklarasi tipe data String";
        System.out.println("KataBaru: " + KataBaru);

        // 3. Deklarasi array satu dimensi integer bernama empatAngka berisi {7, 10, 20, 23}

        int[] empatAngka = {7, 10, 20, 23};
        
        System.out.print("empatAngka: ");
        for (int angka : empatAngka) {
            System.out.print(angka + " ");
        }
        System.out.println();


        // 4. Deklarasi array dua dimensi string bernama Angka berisi angka-angka berikut
        String[][] Angka = {
            {"1", "3", "5"},
            {"14", "19", "20"},
            {"22", "27", "29"}
        };

        System.out.println("Array dua dimensi Angka:");
        for (String[] Angka1 : Angka) {
            for (String item : Angka1) {
                System.out.print(item + " ");
            }
            System.out.println();
        }

        // 5. Deklarasi LinkedList integer bernama listAngka berisi {22, 19, 44, 60, 72}
        LinkedList<Integer> listAngka = new LinkedList<>(Arrays.asList(22, 19, 44, 60, 72));
        System.out.print("listAngka: ");
        for (int nilai : listAngka) {
            System.out.print(nilai + " ");
        }
        System.out.println();
    }
}
