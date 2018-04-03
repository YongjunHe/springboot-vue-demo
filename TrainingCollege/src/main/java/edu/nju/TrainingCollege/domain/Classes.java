package edu.nju.TrainingCollege.domain;

public class Classes {
    private int orderid;
    private int courseid;
    private String semail;
    private int score;

    public Classes() {

    }

    public Classes(int orderid, int courseid, String semail, int score) {
        this.orderid = orderid;
        this.courseid = courseid;
        this.semail = semail;
        this.score = score;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
