import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class ViewConsultation extends JFrame {
    private JPanel consultPanel;
    private JLabel consultHeading;
    private JTable consultTable;
    private DefaultTableModel dtmConsult;
    private JScrollPane consultScrollPane;
    private JButton viewMore;
    private JTextField patientID;
    private JTextField patientName;
    private JTextArea patientNotes;
    public ViewConsultation(){
        setSize(1000,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        consultPanel = new JPanel(new BorderLayout());
        String[] columnNames={"Date","Time Slot","Doctor License No:","Doctor Name","Patient ID","Patient Name","Patient D.O.B","Patient Mobile No:","Cost","Notes"};
        dtmConsult = new DefaultTableModel(columnNames,0);
        consultTable = new JTable(dtmConsult);
        setLocationRelativeTo(null);
        for (Consultation c:UserInterface.consultArrayList) {
            Object[] rowData = {
                    formatter.format(c.getDate()),
                    c.getTime(),
                    c.getDoctor().getLicenceNo(),
                    c.getDoctor().getName()+" "+c.getDoctor().getSurname(),
                    c.getPatient().getPatientId(),
                    c.getPatient().getName()+" "+c.getPatient().getSurname(),
                    c.getPatient().getDateOfBirth(),
                    c.getPatient().getMobile(),
                    c.getCost(),
                    "Encrypted"
            };
            dtmConsult.addRow(rowData);
        }

        consultScrollPane=new JScrollPane(consultTable);
        consultHeading = new JLabel("                                                                                 Consultation Details");
        consultHeading.setFont(new Font("",1,18));
        consultHeading.setForeground(new Color(2, 32, 82));
        consultPanel.setBackground(new Color(11, 135, 212));

        viewMore = new JButton("View More");
        viewMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMore().setVisible(true);
            }
        });
        consultTable.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = consultTable.getSelectedRow();
                patientID.setText(UserInterface.consultArrayList.get(selectedRow).getPatient().getPatientId());
                patientName.setText(UserInterface.consultArrayList.get(selectedRow).getPatient().getName()+" "+UserInterface.consultArrayList.get(selectedRow).getPatient().getSurname());
                patientNotes.setText((new Encryption().decryptData(UserInterface.consultArrayList.get(selectedRow).getNote())));
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
        consultPanel.add("North",consultHeading);
        consultPanel.add("Center", consultScrollPane);
        consultPanel.add("South",viewMore);
        add(consultPanel);
    }

    private JFrame viewMore(){
        JFrame viewMore = new JFrame();
        viewMore.setSize(300,200);
        viewMore.setLocationRelativeTo(null);

        JPanel v1 = new JPanel(new GridLayout(3,3));
        v1.add(new JLabel("        Patient ID:"));
        patientID = new JTextField(15);
        patientID.setEditable(false);
        v1.add(patientID);

        v1.add(new JLabel("        Name:"));
        patientName = new JTextField(15);
        patientName.setEditable(false);
        v1.add(patientName);

        v1.add(new JLabel("        Notes:"));
        patientNotes = new JTextArea(4,15);
        patientNotes.setEditable(false);
        v1.add(patientNotes);

        viewMore.add(v1);
        return viewMore;
    }
}
