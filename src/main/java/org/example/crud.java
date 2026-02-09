package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class crud extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JButton buscarButton;
    private JButton editarButton;
    private JButton crearButton;
    private JButton eliminarButton;
    private JTextField textField1;

    public crud(){
        setTitle("CRUD");
        setSize(800,600);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargar_datos();


        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog("Titulo: ");
                String autor = JOptionPane.showInputDialog("Autor: ");
                String anio = JOptionPane.showInputDialog("Año: ");

                if (titulo.isEmpty()||autor.isEmpty()||anio.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "Campos vacios."
                    );
                }

                try {

                    int aniox = Integer.parseInt(anio);

                    Connection con = conexion.conectar();

                    String sql = "insert into libros (titulo, autor, anio) values (?,?,?)";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, titulo);
                    ps.setString(2, autor);
                    ps.setInt(3, aniox);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    cargar_datos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table1.getSelectedRow();

                if (fila == -1 ){
                    JOptionPane.showMessageDialog(
                            null,
                            "Selecciona una fila"
                    );
                    return;
                }

                int id = Integer.parseInt(table1.getValueAt(fila, 0).toString());
                String otitulo = table1.getValueAt(fila, 1).toString();
                String oautor = table1.getValueAt(fila, 2).toString();
                String oanio = table1.getValueAt(fila, 3).toString();

                String ntitulo = JOptionPane.showInputDialog("Nuevo titulo: ", otitulo);
                String nautor = JOptionPane.showInputDialog("Nuevo titulo: ", oautor);
                String nanio = JOptionPane.showInputDialog("Nuevo titulo: ", oanio);

                try {
                    int anio = Integer.parseInt(nanio);

                    Connection con = conexion.conectar();

                    String sql = "update libros set titulo = ?, autor = ?, anio = ? where id = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, ntitulo);
                    ps.setString(2, nautor);
                    ps.setInt(3, anio);
                    ps.setInt(4, id);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    cargar_datos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table1.getSelectedRow();

                if (fila == -1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Selecciona una fila, porfa."
                    );

                }

                int id = Integer.parseInt(table1.getValueAt(fila, 0).toString());

                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro de esto?",
                        "Confirmacion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION){
                    try {
                        Connection con = conexion.conectar();

                        String sql = "delete from libros where id = ?";
                        PreparedStatement ps = con.prepareStatement(sql);

                        ps.setInt(1, id);
                        ps.executeUpdate();

                        ps.close();
                        con.close();

                        cargar_datos();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String texto = textField1.getText();

                if (texto.isEmpty()){
                    cargar_datos();
                    return;
                }

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("id");
                model.addColumn("titulo");
                model.addColumn("autor");
                model.addColumn("año");

                table1.setModel(model);

                try {
                    Connection con = conexion.conectar();

                    String sql = "select * from libros where titulo like ? or autor like ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, "%"+ texto + "%");
                    ps.setString(2, "%"+ texto + "%");

                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        Object[] fila = new Object[4];
                        fila [0] = rs.getInt("id");
                        fila [1] = rs.getString("titulo");
                        fila [2] = rs.getString("autor");
                        fila [3] = rs.getInt("anio");

                        model.addRow(fila);
                    }
                    ps.close();
                    rs.close();
                    con.close();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
    }

    private void cargar_datos(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("titulo");
        model.addColumn("autor");
        model.addColumn("año");

        table1.setModel(model);

        try {
            Connection con = conexion.conectar();

            String sql = "select * from libros";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Object[] fila = new Object[4];
                fila [0] = rs.getInt("id");
                fila [1] = rs.getString("titulo");
                fila [2] = rs.getString("autor");
                fila [3] = rs.getInt("anio");

                model.addRow(fila);
            }
            ps.close();
            rs.close();
            con.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
