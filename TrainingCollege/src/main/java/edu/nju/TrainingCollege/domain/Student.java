package edu.nju.TrainingCollege.domain;

public class Student {
    private String email;
    private String name;
    private String password;
    private int level;
    private int expense;

    public Student(String email, String name, String password, int level, int expense) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.level = level;
        this.expense = expense;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}
