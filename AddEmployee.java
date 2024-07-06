import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class AddEmployee extends JFrame {
    Container c;
    JLabel labEid, labEname, labSalary;
    JTextField txtEid, txtEname, txtSalary;
    JButton btnSave, btnBack;

    AddEmployee() {
        c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 30, 10);
        c.setLayout(fl);
        c.setBackground(new Color(173, 216, 230));
        Font f = new Font("Calibri", Font.BOLD, 30);

        labEid = new JLabel("Enter emp id ");
        txtEid = new JTextField(15);
        labEname = new JLabel("Enter emp name ");
        txtEname = new JTextField(15);
        labSalary = new JLabel("Enter emp salary ");
        txtSalary = new JTextField(15);
        btnSave = new JButton("Save Employee");
        btnBack = new JButton("Back to Home");

        labEid.setFont(f);
        txtEid.setFont(f);
        labEname.setFont(f);
        txtEname.setFont(f);
        labSalary.setFont(f);
        txtSalary.setFont(f);
        btnSave.setFont(f);
        btnBack.setFont(f);

        c.add(labEid);
        c.add(txtEid);
        c.add(labEname);
        c.add(txtEname);
        c.add(labSalary);
        c.add(txtSalary);
        c.add(btnSave);
        c.add(btnBack);

        ActionListener a1 = (ae) -> {
            Home h = new Home();
            dispose();
        };
        btnBack.addActionListener(a1);

        ActionListener a2 = (ae) -> {
            try {
                if (txtEid.getText().isEmpty() || txtEname.getText().isEmpty() || txtSalary.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(c, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int eid;
                try {
                    eid = Integer.parseInt(txtEid.getText());
                    if (eid <= 0) {
                        throw new NumberFormatException(); // To explicitly trigger the catch block for invalid ID
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(c, "Invalid Employee ID format. Please enter a positive integer", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String ename = txtEname.getText();

                if (ename.trim().isEmpty() || !ename.matches("^[a-zA-Z]+$")) {
                    JOptionPane.showMessageDialog(c, "Invalid name. Name should contain only alphabets", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double salary;

                try {
                    salary = Double.parseDouble(txtSalary.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(c, "Invalid salary format. Please enter a valid numeric value", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

                String url = "jdbc:mysql://localhost:3306/ems8feb24";
                Connection con = DriverManager.getConnection(url, "root", "abc456");

                String checkDuplicateSql = "SELECT * FROM employee WHERE eid = ?";
                PreparedStatement checkDuplicatePst = con.prepareStatement(checkDuplicateSql);
                checkDuplicatePst.setInt(1, eid);
                ResultSet duplicateResult = checkDuplicatePst.executeQuery();

                if (duplicateResult.next()) {
                    JOptionPane.showMessageDialog(c, "Employee with ID " + eid + " already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    con.close();
                    return;
                }

                String insertSql = "INSERT INTO employee VALUES (?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(insertSql);
                pst.setInt(1, eid);
                pst.setString(2, ename);
                pst.setDouble(3, salary);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(c, "Record created");
                txtEid.setText("");
                txtEname.setText("");
                txtSalary.setText("");
                txtEid.requestFocus();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(c, "Issue: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        btnSave.addActionListener(a2);

        setTitle("Add Employee");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
