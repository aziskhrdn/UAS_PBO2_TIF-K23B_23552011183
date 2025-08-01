package com.mycompany.kasirakademik_noer_azis;

public class UserAkademik {
    private String username;
    private String password;
    private int id;

    public UserAkademik(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    // Setter kalau dibutuhkan
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }
}
