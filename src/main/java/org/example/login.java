package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    protected JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarButton;
    private JLabel Error;

    int intentos = 3;

    public login() {

        Error.setText("");

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (intentos == 0){
                    Error.setText(
                            "Intentos alcanzados."
                    );
                    iniciarButton.setEnabled(false);
                    return;
                }

                String usuario = textField1.getText();
                String contra = new String(passwordField1.getPassword());

                if (usuario.isEmpty()||contra.isEmpty()){
                    Error.setText(
                            "Campos vacios.");
                }

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
                                "Bienvenido!"
                        );

                        crud crud = new crud();
                        crud.setVisible(true);
                        SwingUtilities.getWindowAncestor(panel1);
                    } else {
                        intentos--;
                        Error.setText(
                                "Contraseña o Usuario incorrectos. Te faltan: "+ intentos +" intentos."
                        );
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
