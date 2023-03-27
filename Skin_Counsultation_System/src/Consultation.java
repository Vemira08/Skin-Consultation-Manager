import java.io.Serializable;
import java.util.Date;

public class Consultation implements Serializable {
    private Date date;
    private double cost;
    private String note;
    private Doctor doctor;
    private Patient patient;



    private String time;

    public Consultation(Date date, double cost, String note, Doctor doctor, Patient patient,String time) {
        this.date = date;
        this.cost = cost;
        this.note = note;
        this.doctor = doctor;
        this.patient = patient;
        this.time=time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
