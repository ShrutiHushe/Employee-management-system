import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class DeleteEmployee extends JFrame {
    Container c;
    JLabel labEid;
    JTextField txtEid;
    JButton btnDelete, btnBack;

    DeleteEmployee() {
        c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 30, 10);
        c.setLayout(fl);
        c.setBackground(new Color(173, 216, 230));
        Font f = new Font("Calibri", Font.BOLD, 30);

        labEid = new JLabel("Enter emp id to delete ");
        txtEid = new JTextField(15);
        btnDelete = new JButton("Delete Employee");
        btnBack = new JButton("Back to Home");

        labEid.setFont(f);
        txtEid.setFont(f);
        btnDelete.setFont(f);
        btnBack.setFont(f);

        c.add(labEid);
        c.add(txtEid);
        c.add(btnDelete);
        c.add(btnBack);

        ActionListener a1 = (ae) -> {
            Home h = new Home();
            dispose();
        };
        btnBack.addActionListener(a1);

        ActionListener a2 = (ae) -> {
            try {
                if (txtEid.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(c, "Employee ID is required", "Error", JOptionPane.ERROR_MESSAGE);
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

                String sql = "DELETE FROM employee WHERE eid = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, eid);
                int rowsDeleted = pst.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(c, "Record deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(c, "No changes made. Record already deleted");
                }

                txtEid.setText("");
                txtEid.requestFocus();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(c, "Issue: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(c, "Invalid employee ID format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        btnDelete.addActionListener(a2);

        setTitle("Delete Employee");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
