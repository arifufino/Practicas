package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;


public class CRUD {
    protected JPanel panel1;
    private JTextField textField1;
    private JButton inicioButton;
    private JPasswordField passwordField1;


    public CRUD() {
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usuario = textField1.getText();
                String contra = new String(passwordField1.getPassword());

                try {
                    Connection con = conexion.conectar();

                    String sql = "select * from usuarios where usuario = ? and contra = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, usuario);
                    ps.setString(2, contra);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "Entraste!"
                        );
                        menu_principal menu = new menu_principal();
                        menu.setVisible(true);
                        SwingUtilities.getWindowAncestor(panel1).dispose();

                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Contraseña o Usuarios Incorrectos."
                        );
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
