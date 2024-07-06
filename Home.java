import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Home extends JFrame {
    Container c;
    JButton btnAdd, btnView, btnUpdate, btnDelete; 
    Home() {
        c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 30, 30);
        c.setLayout(fl);
	c.setBackground(new Color(173, 216, 230));
        Font f = new Font("Calibri", Font.BOLD, 30);

        btnAdd = new JButton("Add Employee");
        btnView = new JButton("View Employee");
        btnUpdate = new JButton("Update Employee"); 
        btnDelete = new JButton("Delete Employee"); 
        btnAdd.setFont(f);
        btnView.setFont(f);
        btnUpdate.setFont(f);
        btnDelete.setFont(f);

        c.add(btnAdd);
        c.add(btnView);
        c.add(btnUpdate);
        c.add(btnDelete);

        ActionListener a1 = (ae) -> {
            AddEmployee a = new AddEmployee();
            dispose();
        };
        btnAdd.addActionListener(a1);

        ActionListener a2 = (ae) -> {
            ViewEmployee a = new ViewEmployee();
            dispose();
        };
        btnView.addActionListener(a2);

        ActionListener a3 = (ae) -> {
             UpdateEmployee a = new UpdateEmployee();
               dispose();
        };
        btnUpdate.addActionListener(a3);

        ActionListener a4 = (ae) -> {
	       DeleteEmployee a = new DeleteEmployee();
               dispose();
        };
        btnDelete.addActionListener(a4);

this.addWindowListener(new java.awt.event.WindowAdapter() {
@Override
public void windowClosing(java.awt.event.WindowEvent windowEvent) {
if(JOptionPane.showConfirmDialog(c,
"Do u want to exit? ?", "Exit",
JOptionPane.YES_NO_OPTION,
JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

if(JOptionPane.showConfirmDialog(c,
"Are u sure?", "Exit",
JOptionPane.YES_NO_OPTION,
JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
System.exit(0);
}

}
}
});

        setTitle("E. M. S. By Shruti");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
