package edu.nju.TrainingCollege.domain;

import java.sql.Timestamp;

public class Schedule {
    private int courseid;
    private Timestamp starttime;
    private Timestamp endtime;

    public Schedule(int courseid, Timestamp starttime, Timestamp endtime) {
        this.courseid = courseid;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
