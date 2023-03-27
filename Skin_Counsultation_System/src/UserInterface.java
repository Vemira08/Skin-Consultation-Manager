import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class UserInterface extends JFrame implements Serializable {
    public ArrayList<Doctor> doctorsList = WestminsterSkinConsultationManager.doctorsList;
    public static ArrayList<Patient> patientArrayList = loadAllPatients();
    public static ArrayList<Consultation> consultArrayList = loadAllConsultations();


    private JPanel doctorPanel;
    private JLabel doctorHeading;
    private JTable doctorTable;
    private DefaultTableModel dtmDoctor;
    private JScrollPane doctorScrollPane;
    private JButton sortButton;
    private JButton refreshButton;
    private JLabel consultHeading;
    private JTextField txtDocFullName;
    private JTextField txtDocLiNo;
    private JTextField txtDocSpec;
    private JDateChooser dateChooser;
    private JTextField txtTime;
    private JButton checkAvailable;
    private JComboBox<String> selectPatient;
    private JTextField txtPatientID;
    private JTextField txtPatientFirstName;
    private JTextField txtPatientSurname;
    private JTextField txtPatientDOB;
    private JTextField txtPatientMobileNo;
    private JTextField txtNoOfHours;
    private JTextField txtCostPerHour;
    private JTextField txtTotalCost;
    private JTextArea txtNotes;
    private JButton clearFields;
    private JButton addConsultation;
    private JButton viewConsultation;

    public UserInterface(){
        setSize(1100,600);
        setTitle("Westminster Skin Consultation");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("WESTMINSTER SKIN CONSULTATION");
        headingLabel.setForeground(new Color(2, 32, 82));
        headingLabel.setFont(new Font("",1,25));
        headingPanel.add(headingLabel);
        add("North",headingPanel);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(2,1));
        bodyPanel.add(doctorDetailsPane());
        bodyPanel.add(consultDetails());
        add("Center",bodyPanel);
        add("South",bottomPanel());
        setResizable(false);
        setVisible(true);
    }

    private JPanel doctorDetailsPane() {
        doctorPanel = new JPanel(new BorderLayout());
        String[] columnNames={"Surname","First Name","DOB","Mobile No:","License No:","Specialization"};
        dtmDoctor = new DefaultTableModel(columnNames,0);
        doctorTable = new JTable(dtmDoctor);
        for (Doctor d: doctorsList) {
            String[] rowData ={
                    d.getSurname(),
                    d.getName(),
                    d.getDateOfBirth(),
                    d.getMobile(),
                    String.valueOf(d.getLicenceNo()), //Casting integer to string
                    d.getSpecialization()
            };
            dtmDoctor.addRow(rowData);
        }
        doctorTable.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = doctorTable.getSelectedRow();
                txtDocFullName.setText(doctorsList.get(selectedRow).getSurname()+" "+doctorsList.get(selectedRow).getName());
                txtDocLiNo.setText(doctorsList.get(selectedRow).getLicenceNo()+"");
                txtDocSpec.setText(doctorsList.get(selectedRow).getSpecialization());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        doctorScrollPane=new JScrollPane(doctorTable);
        doctorHeading = new JLabel("                                                                                      Doctors Details");
        doctorHeading.setFont(new Font("",1,18));
        doctorHeading.setForeground(new Color(2, 32, 82));
        doctorPanel.setBackground(new Color(11, 135, 212));
        doctorPanel.add("North",doctorHeading);
        doctorPanel.add("Center", doctorScrollPane);
        JPanel doctorButtonPanel = new JPanel();
        sortButton = new JButton("      Sort Doctors      ");
        sortButton.setBackground(new Color(2, 32, 82));
        sortButton.setForeground(Color.WHITE);
        refreshButton = new JButton("         Refresh         ");
        refreshButton.setBackground(new Color(2, 32, 82));
        refreshButton.setForeground(Color.WHITE);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dtmDoctor.setRowCount(0);     //Remove all the doctors from doctors table
                for (Doctor d: WestminsterSkinConsultationManager.sortDoctorsList()) {
                    String[] rowData ={
                            d.getSurname(),
                            d.getName(),
                            d.getDateOfBirth(),
                            d.getMobile(),
                            String.valueOf(d.getLicenceNo()), //Casting integer to string
                            d.getSpecialization()
                    };
                    dtmDoctor.addRow(rowData);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dtmDoctor.setRowCount(0);//Remove all the doctors from doctors table
                doctorsList = WestminsterSkinConsultationManager.doctorsList;
                for (Doctor d: doctorsList) {
                    String[] rowData ={
                            d.getSurname(),
                            d.getName(),
                            d.getDateOfBirth(),
                            d.getMobile(),
                            String.valueOf(d.getLicenceNo()), //Casting integer to string
                            d.getSpecialization()
                    };
                    dtmDoctor.addRow(rowData);
                }
            }
        });
        doctorButtonPanel.add(refreshButton);
        doctorButtonPanel.add(new JLabel("    "));
        doctorButtonPanel.add(sortButton);
        doctorPanel.add("South",doctorButtonPanel);
        return doctorPanel;
    }
    private JPanel consultDetails(){
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(11, 135, 212));
        consultHeading = new JLabel("\n                                                                                 Booking a Consultation");
        consultHeading.setFont(new Font("",1,18));
        consultHeading.setForeground(new Color(2, 32, 82));
        detailsPanel.add("North",consultHeading);
        JPanel bodyPanel = new JPanel(new GridLayout(1,3));

        bodyPanel.add(doctorDetails());

        bodyPanel.add(patientDetails());

        bodyPanel.add(lastDetails());
        detailsPanel.add(bodyPanel);

        return detailsPanel;

    }
    private JPanel doctorDetails(){
        JPanel firstRowPanel = new JPanel(new GridLayout(6,2,20,13));
        firstRowPanel.add(new JLabel("Doctor Full Name:"));
        txtDocFullName = new JTextField(10);
        txtDocFullName.setEditable(false);
        firstRowPanel.add(txtDocFullName);

        firstRowPanel.add(new JLabel("License No:"));
        txtDocLiNo = new JTextField(10);
        txtDocLiNo.setEditable(false);
        firstRowPanel.add(txtDocLiNo);

        firstRowPanel.add(new JLabel("Specialization:"));
        txtDocSpec = new JTextField(10);
        txtDocSpec.setEditable(false);
        firstRowPanel.add(txtDocSpec);

        firstRowPanel.add(new JLabel("Consultation Date:"));
        dateChooser = new JDateChooser();
        firstRowPanel.add(dateChooser);

        firstRowPanel.add(new JLabel("Time(24 hours):"));
        txtTime = new JTextField(10);
        firstRowPanel.add(txtTime);

        firstRowPanel.add(new JLabel(""));
        checkAvailable = new JButton("Check Availability");
        checkAvailable.setForeground(Color.WHITE);
        checkAvailable.setBackground(new Color(163, 8, 29));
        checkAvailable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Date date=dateChooser.getDate();
                    if(date==null){
                        message("Please Select Date");
                        return;
                    }
                    try{
                        int id =Integer.parseInt(txtDocLiNo.getText());
                        boolean doctorBusy = isDoctorBusy(id,date);
                        if(doctorBusy){
                            message("Doctor is Busy");
                        }else {
                            message("Doctor is Available");
                        }
                    }catch (Exception exception){
                        message("Please Select Doctor");
                    }

            }
        });
        firstRowPanel.add(checkAvailable);

        return firstRowPanel;
    }

    private JPanel patientDetails(){
        JPanel secondColumnPanel = new JPanel(new GridLayout(6,2,10,13));
        secondColumnPanel.add(new JLabel("        Select Existing Patient:"));
        selectPatient = new JComboBox(patientNames());
        secondColumnPanel.add(selectPatient);
        txtCostPerHour=new JTextField(15);
        txtCostPerHour.setText(String.valueOf(15.0));
        txtCostPerHour.setEditable(false);

        selectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = selectPatient.getSelectedIndex();
                txtPatientID.setText(patientArrayList.get(index).getPatientId());
                txtPatientSurname.setText(patientArrayList.get(index).getSurname());
                txtPatientFirstName.setText(patientArrayList.get(index).getName());
                txtPatientDOB.setText(patientArrayList.get(index).getDateOfBirth());
                txtPatientMobileNo.setText(patientArrayList.get(index).getMobile());
                txtPatientSurname.setEditable(false);
                txtPatientFirstName.setEditable(false);
                txtPatientDOB.setEditable(false);
                txtPatientMobileNo.setEditable(false);
                txtCostPerHour.setText(String.valueOf(25.0));
            }
        });

        secondColumnPanel.add(new JLabel("             Patient ID:"));
        txtPatientID = new JTextField(15);
        txtPatientID.setEditable(false);
        txtPatientID.setText(getPatientID());
        secondColumnPanel.add(txtPatientID);

        secondColumnPanel.add(new JLabel("             First Name:"));
        txtPatientFirstName = new JTextField(15);
        secondColumnPanel.add(txtPatientFirstName);

        secondColumnPanel.add(new JLabel("             Surname:"));
        txtPatientSurname = new JTextField(15);
        secondColumnPanel.add(txtPatientSurname);

        secondColumnPanel.add(new JLabel("             D.O.B(DD-MM-YYYY):"));
        txtPatientDOB = new JTextField(15);
        secondColumnPanel.add(txtPatientDOB);

        secondColumnPanel.add(new JLabel("             Mobile No:"));
        txtPatientMobileNo = new JTextField(15);
        secondColumnPanel.add(txtPatientMobileNo);

        return secondColumnPanel;
    }


    private JPanel lastDetails(){
        JPanel thirdColumnPanel = new JPanel(new GridLayout(6,2,10,5));

        thirdColumnPanel.add(new JLabel("             Add Some Notes: "));
        txtNotes = new JTextArea(1,15);
        thirdColumnPanel.add(txtNotes);

        thirdColumnPanel.add(new JLabel("             No of Hours: "));
        txtNoOfHours = new JTextField(15);
        thirdColumnPanel.add(txtNoOfHours);

        thirdColumnPanel.add(new JLabel("             Cost Per Hour:"));
        thirdColumnPanel.add(txtCostPerHour);

        thirdColumnPanel.add(new JLabel("             Total Cost: "));
        txtTotalCost = new JTextField(15);
        thirdColumnPanel.add(txtTotalCost);
        txtTotalCost.setEditable(false);


        return thirdColumnPanel;
    }
    private JPanel bottomPanel(){
        JPanel bottomButtonPanel = new JPanel();
        clearFields = new JButton("            Clear Fields            ");
        clearFields.setBackground(new Color(2, 32, 82));
        clearFields.setForeground(Color.WHITE);
        clearFields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
            }
        });
        addConsultation = new JButton("       Add Consultation      ");
        addConsultation.setBackground(new Color(2, 32, 82));
        addConsultation.setForeground(Color.WHITE);
        viewConsultation = new JButton("  View All Consultations  ");
        viewConsultation.setBackground(new Color(2, 32, 82));
        viewConsultation.setForeground(Color.WHITE);

        bottomButtonPanel.add(clearFields);
        bottomButtonPanel.add(new JLabel("    "));
        bottomButtonPanel.add(addConsultation);
        bottomButtonPanel.add(new JLabel("    "));
        bottomButtonPanel.add(viewConsultation);


        addConsultation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor doctor=null;
                double cost=0;
                try {
                    int docID = Integer.parseInt(txtDocLiNo.getText());
                    for (Doctor d: doctorsList) {
                        if(d.getLicenceNo()==docID){
                            doctor = d;
                            break;
                        }
                    }
                }catch (Exception ex){
                    message("Please select Doctor");
                    return;
                }


                Patient patient = new Patient(
                        txtPatientID.getText(),
                        txtPatientFirstName.getText(),
                        txtPatientSurname.getText(),
                        txtPatientDOB.getText(),
                        txtPatientMobileNo.getText()
                );

                if(isValidPatient(patient)){
                    if(!isPatientExist(patient.getPatientId())){
                        patientArrayList.add(patient);
                        saveData();
                        cost=15;
                    }else{
                        cost=25;
                    }
                    txtCostPerHour.setText(String.valueOf(cost));

                    Date date = dateChooser.getDate();
                    if(date!=null) {
                        boolean doctorBusy = isDoctorBusy(doctor.getLicenceNo(), date);
                        if(isHoursCorrect()) {
                            txtTotalCost.setText(String.valueOf(calculateTotalCost()));
                            if(!doctorBusy){
                                Consultation consult = new Consultation(date, Double.parseDouble(txtTotalCost.getText()), (new Encryption().encryptData(txtNotes.getText())), doctor, patient, txtTime.getText());
                                consultArrayList.add(consult);
                                saveData();
                                message("Consultation added successfully...");
                            }else {
                                doctor = getRandomDoctor();
                                message("The relevant doctor is busy. \nDr."+doctor.getName()+" "+doctor.getSurname()+" added for the consultation");
                                Consultation consult = new Consultation(date, Double.parseDouble(txtTotalCost.getText()), txtNotes.getText(), doctor, patient,txtTime.getText());
                                consultArrayList.add(consult);
                                saveData();
                            }
                            clearFields.doClick();
                        }
                    }else{
                        message("Please Select Date");
                    }


                }else{
                    message("invalid Patient");
                }
                if(patientArrayList.size()>selectPatient.getItemCount()){
                    Patient patient1=patientArrayList.get(patientArrayList.size()-1);
                    selectPatient.addItem(patient1.getName()+" "+patient1.getSurname());
                }



            }
        });
        viewConsultation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 new ViewConsultation().setVisible(true);


            }
        });

        return  bottomButtonPanel;
    }
    private double calculateTotalCost(){
        double totalCost = Double.parseDouble(txtCostPerHour.getText()) * Double.parseDouble(txtNoOfHours.getText());
        return totalCost;
    }

    private boolean isHoursCorrect(){
        try{
            int hours = Integer.parseInt(txtNoOfHours.getText());
            return true;
        }catch (Exception e){
            message("Enter Correct No: of hours");
            txtNoOfHours.setText("");
            return false;
        }
    }

    private boolean isPatientExist(String pID){
        for (Patient p: patientArrayList) {
            if(p.getPatientId().equalsIgnoreCase(pID)){
                return true;
            }
        }return false;
    }

    private boolean isValidPatient(Patient p){
        if(!(p.getName().trim().equalsIgnoreCase(""))){
            return true;
        }else{
            message("Please enter patient's first name..!");
            return false;
        }
    }

    private void message(String msg){
        JOptionPane op = new JOptionPane(msg,JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = op.createDialog("message");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
    private boolean isDoctorBusy(int licenseNo,Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (Consultation c: consultArrayList) {
            if(format.format(c.getDate()).equalsIgnoreCase(format.format(date))){
                if(c.getDoctor().getLicenceNo()==licenseNo){
                        return true;
                }
            }
        }
        return false;
    }
    private String[] patientNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Patient p:patientArrayList) {
            names.add(p.getPatientId()+":"+p.getName()+" "+p.getSurname());
        }
        String[] namesList = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            namesList[i]=names.get(i);
        }
        return namesList;
    }
    private void clearAllFields(){
        dateChooser.setDate(null);
        txtDocFullName.setText("");
        txtDocLiNo.setText("");
        txtDocSpec.setText("");
        txtTime.setText("");
        txtPatientID.setText(getPatientID());
        txtPatientFirstName.setText("");
        txtPatientSurname.setText("");
        txtPatientDOB.setText("");
        txtNotes.setText("");
        txtPatientMobileNo.setText("");
        txtCostPerHour.setText("");
        txtNoOfHours.setText("");
        txtTotalCost.setText("");

        txtPatientSurname.setEditable(true);
        txtPatientFirstName.setEditable(true);
        txtPatientDOB.setEditable(true);
        txtPatientMobileNo.setEditable(true);
        txtCostPerHour.setText("15.0");

    }
    private String getPatientID(){
        return "P"+ (consultArrayList.size()+1001);
    }

    private Doctor getRandomDoctor(){
        ArrayList<Doctor> randomDoctorsList = new ArrayList<>();
        try {
            for (Doctor d : doctorsList) {
                randomDoctorsList.add(d);
            }

            for (Consultation c : consultArrayList) {
                for (Doctor d : doctorsList) {
                    if (c.getDoctor() == d) {
                        if (isDoctorBusy(d.getLicenceNo(), c.getDate())) {
                            randomDoctorsList.remove(d);
                        }
                    }
                }
            }
            Random input = new Random();
            int index = input.nextInt(randomDoctorsList.size());

            return randomDoctorsList.get(index);
        } catch (Exception e) {
            System.out.println("All the doctors are busy..! Select Another date..!");
        }
        return null;
    }

    public void saveData(){
        try{
            //Save data from patient array list
            FileOutputStream fileOutputStream1 = new FileOutputStream("Patients.ser");
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(fileOutputStream1);
            ArrayList<Patient> tempPatients = new ArrayList<>();
            tempPatients.addAll(patientArrayList);
            objectOutputStream1.writeObject(tempPatients);

            //Save data from consultations array list
            FileOutputStream fileOutputStream2 = new FileOutputStream("Consultations.ser");
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream2);
            ArrayList<Consultation> tempConsultations = new ArrayList<>();
            tempConsultations.addAll(consultArrayList);
            objectOutputStream2.writeObject(tempConsultations);


        }catch(IOException e){
            throw new RuntimeException();
        }
    }

    public static ArrayList<Patient> loadAllPatients(){
        try{
            FileInputStream fileInputStream = new FileInputStream("Patients.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Patient> temp = (ArrayList<Patient>) objectInputStream.readObject();
            return temp;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    public static ArrayList<Consultation> loadAllConsultations(){
        try{
            FileInputStream fileInputStream = new FileInputStream("Consultations.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Consultation> temp = (ArrayList<Consultation>) objectInputStream.readObject();
            return temp;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
}
