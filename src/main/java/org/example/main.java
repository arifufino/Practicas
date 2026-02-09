package org.example;

import javax.swing.*;

public class main {

    public static void main(String[] args) {
        conexion.conectar();


        JFrame frame = new JFrame("Biblioteca crud");
        frame.setSize(700,700);
        frame.setContentPane(new login().panel1);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
