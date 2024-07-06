import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class ViewEmployee extends JFrame {
    Container c;
    JTextArea toData;
    JButton btnBack;

    ViewEmployee() {
        c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 30, 10);
        c.setLayout(fl);
	c.setBackground(new Color(173, 216, 230));
        Font f = new Font("Calibri", Font.BOLD, 30);

        toData = new JTextArea(10, 15);
        toData.setEditable(false);
        btnBack = new JButton("Back to home");

        toData.setFont(f);
        btnBack.setFont(f);

        c.add(toData);
        c.add(btnBack);

        ActionListener a1 = (ae) -> {
            Home h = new Home();
            dispose();
        };
        btnBack.addActionListener(a1);

        try {
            // Load the driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            // Connect to the db
            String url = "jdbc:mysql://localhost:3306/ems8feb24";
            Connection con = DriverManager.getConnection(url, "root", "abc456");

            // Do some operation CRUD
            String sql = "select * from employee";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int eid = rs.getInt("eid");
                String ename = rs.getString("ename");
                double salary = rs.getDouble("salary"); 

                // Append the data to TextArea
                toData.append(eid + "   " + ename + "   " + salary + "\n");
            }

            // Close the connection
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, "SQL issue: " + e);
        }

        setTitle("View Employee");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
