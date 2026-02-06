package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        conexion.conectar();

        JFrame frame = new JFrame("login biblioteca");
        frame.setContentPane(new CRUD().panel1);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
