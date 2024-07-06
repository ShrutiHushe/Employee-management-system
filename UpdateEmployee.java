import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class UpdateEmployee extends JFrame {
    Container c;
    JLabel labEid, labEname, labSalary;
    JTextField txtEid, txtEname, txtSalary;
    JButton btnSave, btnBack;

    UpdateEmployee() {
        c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 30, 10);
        c.setLayout(fl);
        c.setBackground(new Color(173, 216, 230));
        Font f = new Font("Calibri", Font.BOLD, 30);

        labEid = new JLabel("Enter emp id ");
        txtEid = new JTextField(15);
        labEname = new JLabel("Enter updated emp name ");
        txtEname = new JTextField(15);
        labSalary = new JLabel("Enter updated emp salary ");
        txtSalary = new JTextField(15);
        btnSave = new JButton("Update Employee");
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
                        JOptionPane.showMessageDialog(c, "Employee ID must be a positive integer", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(c, "Invalid Employee ID format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String ename = txtEname.getText();
                if (!ename.matches("^[a-zA-Z]+$")) {
                    JOptionPane.showMessageDialog(c, "Invalid name. Name should contain only alphabets", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double salary;
                try {
                    salary = Double.parseDouble(txtSalary.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(c, "Invalid salary format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

                String url = "jdbc:mysql://localhost:3306/ems8feb24";
                Connection con = DriverManager.getConnection(url, "root", "abc456");

                String checkIdSql = "SELECT * FROM employee WHERE eid = ?";
                PreparedStatement checkIdPst = con.prepareStatement(checkIdSql);
                checkIdPst.setInt(1, eid);
                ResultSet idResult = checkIdPst.executeQuery();

                if (!idResult.next()) {
                    JOptionPane.showMessageDialog(c, "Employee with ID " + eid + " does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    con.close();
                    return;
                }

                String sql = "UPDATE employee SET ename = ?, salary = ? WHERE eid = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, ename);
                pst.setDouble(2, salary);
                pst.setInt(3, eid);
                int rowsUpdated = pst.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(c, "Record updated successfully");
                } else {
                    JOptionPane.showMessageDialog(c, "No changes made. Record already up-to-date");
                }

                txtEid.setText("");
                txtEname.setText("");
                txtSalary.setText("");
                txtEid.requestFocus();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(c, "Issue: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(c, "Invalid salary format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        btnSave.addActionListener(a2);

        setTitle("Update Employee");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
