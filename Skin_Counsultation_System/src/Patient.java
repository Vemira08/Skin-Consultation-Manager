import java.io.Serializable;

public class Patient extends Person implements Serializable {
    private String patientId;

    public Patient( String patientId,String name, String surname, String dateOfBirth, String mobile) {
        super(name, surname, dateOfBirth, mobile);
        this.patientId = patientId;
    }



    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
