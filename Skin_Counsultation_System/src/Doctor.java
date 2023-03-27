import java.io.Serializable;

public class Doctor extends Person implements Comparable<Doctor>, Serializable {
    private int licenceNo;
    private String specialization;

    public Doctor() {}

    public Doctor(String name, String surname, String dateOfBirth, String mobile, int licenceNo, String specialization) {
        super(name, surname, dateOfBirth, mobile);
        this.licenceNo = licenceNo;
        this.specialization = specialization;
    }

    public int getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(int licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return String.format("|%-15s",super.getSurname()) +
                String.format("|%-15s",super.getName())+
                String.format("|%15s",super.getDateOfBirth())+
                String.format("|%15s",super.getMobile())+
                String.format("|%15s",this.licenceNo)+
                String.format("|%-15s|",this.specialization);
    }

    @Override
    public int compareTo(Doctor doctor) { //Overriding the compareTo method for sort the doctors list
        int value = this.getSurname().toLowerCase().compareTo(doctor.getSurname().toLowerCase());
        return value > 0 ? 1 : value == 0 ? 0 : -1;
    }
}
