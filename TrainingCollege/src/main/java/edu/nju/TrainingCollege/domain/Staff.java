package edu.nju.TrainingCollege.domain;

public class Staff {
    private String email;
    private String name;
    private String password;
    private String title;
    private String college;

    public Staff(String email, String name, String password, String title, String college) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.title = title;
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
