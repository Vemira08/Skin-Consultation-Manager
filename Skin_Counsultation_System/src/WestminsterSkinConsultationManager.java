import java.io.*;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    static ArrayList<Doctor> doctorsList = new ArrayList<>();

    public WestminsterSkinConsultationManager(){
        doctorsList = loadDoctors();     //Load all the previous doctors that you entered.
        menu();
    }



    public void menu(){
        String exitOption = " ";
        while(!exitOption.equalsIgnoreCase("y")){
            Scanner input = new Scanner(System.in);
            System.out.println("+------------------------------Welcome to Skin Consultation Manager------------------------------+");
            System.out.println();
            System.out.println(" 1 : Add doctor  ");
            System.out.println(" 2 : Delete a doctor ");
            System.out.println(" 3 : View all doctors ");
            System.out.println(" 4 : Save doctors");
            System.out.println(" 5 : User Interface ");

            System.out.println("+-------------------------------------------------------------------------------------------------+");
            System.out.print("Enter here: ");
            try{
                String menuOption = input.next();
                int option = Integer.parseInt(menuOption);
                switch (option) {
                    case 1:
                        addNewDoctor();
                        break;
                    case 2:
                        deleteDoctor();
                        break;
                    case 3:
                        printDoctorsList();
                        break;
                    case 4: System.out.println(saveFile());
                        break;
                    case 5: new UserInterface();
                        break;
                    default:
                        System.out.println("Please enter a correct option (1-5)");
                }
                System.out.println("If you want to terminate press 'Y' or to return main menu, press any other key...");
                System.out.print("Enter here :");
                exitOption = input.next();
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Please enter an Integer...!");
            }
        }
    }

    @Override
    public void addNewDoctor() {
        if (doctorsList.size() < 10) {
            System.out.println();
            System.out.println("+------------------------------------ADD A NEW DOCTOR------------------------------------+");
            System.out.println("ENTER FOLLOWING DETAILS CORRECTLY");
            System.out.println();
            boolean add = doctorsList.add(getDoctor()); //Assigning to the boolean variable that add method in arraylist is working completely and properly
            if (add) {
                System.out.println("Doctor added to the system successfully...");
            }
        } else { //If the doctorsList.size() == 10, then can't add the doctors unless you delete at least one doctor
            System.out.println("CAN'T ADD A DOCTOR. LIST IS FULL..!");
        }
    }

    public Doctor getDoctor() { //This method is for returning a new doctor and pass it to the addNewDoctor method.
        int liNo;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Medical Licence Number        : ");
            String licenceNo = input.next();
            try {
                liNo = Integer.parseInt(licenceNo);  //Check whether entered license number is compatible with integer type.If not exception will be raised.
                if (isDoctorExists(liNo) != -1) {
                    System.out.println("THE RELEVANT DOCTOR IS ALREADY EXISTS...!");
                }else{
                    break;
                }
            } catch (Exception e) {
                System.out.println("PLEASE ENTER A VALID LICENCE NUMBER..!");
            }
        }
        System.out.print("First Name                    : ");
        String firstName = input.next();
        System.out.print("Surname                       : ");
        String surname = input.next();
        System.out.print("Date of Birth(DD-MM-YYYY)     : ");
        String dob = input.next();
        System.out.print("Mobile Number                 : ");
        String mobile = input.next();
        System.out.print("Specialization                : ");
        String specialization = input.next();

        return new Doctor(firstName, surname, dob, mobile, liNo, specialization);  //Returning a new doctor object to passing the add method.
    }

    private int isDoctorExists(int licenceNo) {  //Check whether the doctor is existing for relevant license number that entered by user
        for (int i = 0; i < doctorsList.size(); i++) {
            if (doctorsList.get(i).getLicenceNo() == licenceNo) {
                return i;
            }
        }
        return -1;
    }
    private  boolean confirmation() {
        Scanner input = new Scanner(System.in);
        String option = input.next();
        return option.equalsIgnoreCase("y");
    }
    @Override
    public void deleteDoctor() {
        Scanner input = new Scanner(System.in);
        int liNo;
        System.out.println("+------------------------------------DELETE A DOCTOR------------------------------------+");
        while (true) {
            System.out.print("Enter the doctor's licence number: ");
            String licenceNo = input.next();
            try {
                liNo = Integer.parseInt(licenceNo);
                int index = isDoctorExists(liNo); //Check whether entered license number is compatible with integer type.If not exception will be raised.
                if (index != -1) {
                    System.out.println("Enter Y to delete | Any other key to cancel"); //Ask from the user that he exactly want to delete that doctor from the system.
                    if (confirmation()) {
                        doctorsList.remove(index);
                        System.out.println("Doctor is successfully deleted...");
                        break;
                    } else {
                        System.out.println("Deletion cancelled...");
                        break;
                    }
                } else {
                    System.out.println("THIS DOCTOR DOES NOT EXISTS IN THE SYSTEM...!");
                    System.out.println("Enter Y to delete another doctor | Any other key to Main Menu...");
                    if (!confirmation()) {
                        break;
                    }
                }

            } catch (Exception e) {
                System.out.println("PLEASE ENTER A VALID LICENCE NUMBER..!");
            }
        }
    }
    @Override
    public void printDoctorsList() {
        System.out.println("+------------------------------------------------------------------------------------------------+");
        System.out.println(String.format("|%-15s","Surname")+String.format("|%-15s","First Name")
                +String.format("|%-15s","D.O.B")+String.format("|%-15s","Mobile")
                +String.format("|%-15s","Li.No.")+String.format("|%-15s|","Specialization"));
        System.out.println("+------------------------------------------------------------------------------------------------+");
        for (Doctor d: sortDoctorsList()) { //Passing the sorted doctors list by surname to print list of doctors.
            System.out.println(d);
        }
        System.out.println("+------------------------------------------------------------------------------------------------+");
        System.out.println();
    }
    public static ArrayList<Doctor> sortDoctorsList(){
        ArrayList<Doctor> temp = doctorsList;
        Collections.sort(temp);
        return temp;
    }

    @Override
    public String saveFile() {
        try {

            FileWriter writer = new FileWriter("Doctors.txt"); //This filewriter for save the doctors list to text file.
            writer.write("Full Name\t\tDate of Birth\tMobile Number\tM L Number\tSpecialization\n");
            writer.write("------------------------------------------------------------------------------------------\n");
            for (Doctor d:doctorsList) {
                writer.write(stringDoctor(d));
            }
            FileOutputStream outputStream = new FileOutputStream("Doctors.ser"); //Saving the objects
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(doctorsList);
            writer.flush();
            return "SAVED...";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {

        }
    }

    public String stringDoctor(Doctor d){
        return d.getName()+"\t"+d.getSurname()+"\t|"+d.getDateOfBirth()+"\t|"+d.getMobile()+ "\t|"+d.getLicenceNo()+"\t\t|"+d.getSpecialization()+" \n";
    }

    public ArrayList<Doctor> loadDoctors(){ //Loading the doctors when start run the program
        try {
            FileInputStream fileInputStream = new FileInputStream("Doctors.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Doctor> temp = (ArrayList<Doctor>) objectInputStream.readObject();

            return temp;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
