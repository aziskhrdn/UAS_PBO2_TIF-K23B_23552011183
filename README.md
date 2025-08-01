# Final Proyek Pemrograman Berorientasi Obyek 2

**Mata Kuliah:** Pemrograman Berorientasi Obyek 2  
**Dosen Pengampu:** Muhammad Ikhwan Fathulloh



## Profil
- **Nama**: Noer Azis Khaerudin  
- **NIM**: 23552011183 
- **Studi Kasus**: Kasir Akademik


## Judul Studi Kasus

Sistem Kasir Akademik untuk Pengelolaan Data Mahasiswa, Tagihan, dan Transaksi Pembayaran



## Penjelasan Studi Kasus

Aplikasi ini dirancang untuk mempermudah proses administrasi pembayaran di lingkungan akademik. Aplikasi ini secara khusus digunakan oleh pihak akademik untuk mengelola berbagai data terkait keuangan mahasiswa.

Fungsi utama dari aplikasi ini meliputi:

- Manajemen Data Mahasiswa : Akademik dapat menambahkan, memperbarui, dan menghapus data mahasiswa sesuai kebutuhan administratif.

- Manajemen Data Tagihan : Akademik dapat membuat dan mengelola tagihan pembayaran untuk setiap mahasiswa berdasarkan semester atau kebutuhan lainnya.

- Manajemen Data Pembayaran (Transaksi) = Akademik mencatat pembayaran yang dilakukan mahasiswa dan memantau status pelunasan tagihan.

Sesuai dengan nama studi kasusnya, yaitu Kasir Akademik, maka seluruh aktivitas dalam aplikasi ini hanya dapat dilakukan oleh pihak akademik. Proses pengelolaan data sepenuhnya dikendalikan oleh pengguna dengan peran akademik (admin akademik).

---

## 4 Pilar OOP dalam Studi Kasus


### 1. Inheritance (Pewarisan)

Konsep pewarisan digunakan untuk membangun hierarki objek yang logis dan mengurangi duplikasi kode. Dalam proyek ini, terdapat hierarki kelas yang merepresentasikan entitas akademik (`Sivitas`).

**Sivitas.java**

```java
public abstract class Sivitas {
    protected int id;
    protected String nama;

    public Sivitas(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
}
```

**Civitas.java**

```java
public class Civitas extends Sivitas {
    public Civitas(int id, String nama) {
        super(id, nama);
    }
}
```

**Dosen.java**

```java
public class Dosen extends Civitas {
    private String nidn;

    public Dosen(int id, String nama, String nidn) {
        super(id, nama);
        this.nidn = nidn;
    }

    public String getNidn() { return nidn; }
}
```
---

### 2. Encapsulation (Enkapsulasi)

Enkapsulasi adalah pilar yang paling dominan dalam proyek ini. Setiap kelas model data (misalnya Mahasiswa, Tagihan, Transaksi) menyembunyikan data internalnya dari akses luar dan menyediakan antarmuka yang terkontrol melalui metode getter dan setter publik.
Hal ini memastikan integritas data dan mencegah modifikasi data yang tidak sah.

Contoh kode Mahasiswa yang menunjukkan enkapsulasi:

**Mahasiswa.java**

```Java
public class Mahasiswa {
    private int id;
    private String nama;
    private String nim;
    private int userId;

    public String getNama() {
        return nama; // Getter untuk mengakses data nama
    }

    public void setNama(String nama) {
        this.nama = nama; // Setter untuk mengubah data nama
    }
    // ... getter dan setter lainnya ...
}
```

Atribut id, nama, nim, dan userId bersifat private, sehingga tidak bisa diakses langsung dari kelas lain.

### 3. Polymorphism (Polimorfisme)

Meskipun tidak diimplementasikan secara eksplisit di kode yang ada karena inkonsistensi pewarisan, polimorfisme bisa diterapkan dengan mudah. Polimorfisme memungkinkan objek dari kelas yang berbeda (Dosen dan Mahasiswa) untuk diperlakukan sebagai objek dari kelas induk yang sama (Civitas).

Contoh penerapan polimorfisme (dengan asumsi Mahasiswa mewarisi Civitas):
Sebuah metode tampilkanInfoCivitas dapat menerima objek Civitas dan bekerja baik untuk Dosen maupun Mahasiswa.

```Java
// Contoh method polimorfik
public void tampilkanInfoCivitas(Civitas civitas) {
    System.out.println("ID: " + civitas.getId());
    System.out.println("Nama: " + civitas.getNama());

    // Dengan polymorphism, kita bisa memeriksa tipe objek
    if (civitas instanceof Dosen) {
        Dosen dosen = (Dosen) civitas;
        System.out.println("NIDN: " + dosen.getNidn());
    } else if (civitas instanceof Mahasiswa) {
        Mahasiswa mahasiswa = (Mahasiswa) civitas;
        System.out.println("NIM: " + mahasiswa.getNim());
    }
}
```

### 4. Abstract (Abstraksi)
   
Abstraksi digunakan untuk mendefinisikan kerangka atau cetak biru umum untuk sekelompok objek tanpa harus mengimplementasikan semua detailnya. Dalam proyek ini, kelas Sivitas adalah contoh yang jelas dari abstraksi.

Sivitas adalah kelas abstract yang mendefinisikan atribut dasar (id, nama) yang harus dimiliki oleh setiap entitas akademik.

Kelas ini tidak dapat dibuat objeknya secara langsung, tetapi berfungsi sebagai dasar untuk kelas-kelas konkret seperti Dosen atau Mahasiswa.

Contoh kode Sivitas yang menunjukkan abstraksi:

```Java
public abstract class Sivitas {
    protected int id;
    protected String nama;

    public Sivitas(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
}
```

---

## Demo Proyek
* **YouTube**:
