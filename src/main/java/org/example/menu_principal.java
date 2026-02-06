package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class menu_principal extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton buscarButton;
    private JButton borrarButton;
    private JButton crearButton;
    private JButton editarButton;
    private JTextField textField1;

    public menu_principal() {

        setTitle("menu principal - biblioteca");
        setContentPane(panel1);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargar_tabla();

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila  =  table1.getSelectedRow();

                if (fila == -1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Selecciona una fila."
                    );
                    return;
                }

                int id = Integer.parseInt(table1.getValueAt(fila, 0).toString());

                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro quieres eliminar?",
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

                        cargar_tabla();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog("Inserta titulo");
                String autor = JOptionPane.showInputDialog("Inserta autor");
                String aniotxt = JOptionPane.showInputDialog("Inserta año");

                if (titulo.isEmpty()||autor.isEmpty()||aniotxt.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "datos incompletos"
                    );
                    return;
                }

                try {
                    int anio = Integer.parseInt(aniotxt);

                    Connection con = conexion.conectar();

                    String sql = "insert into libros (titulo, autor, anio) values (?,?,?)";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, titulo);
                    ps.setString(2, autor);
                    ps.setInt(3, anio);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    JOptionPane.showMessageDialog(
                            null,
                            "libro agregado"
                    );
                    cargar_tabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = table1.getSelectedRow();

                if (fila == -1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Seleccione alguna fila."
                    );
                    return;
                }

                int id = Integer.parseInt(table1.getValueAt(fila, 0).toString());
                String tituloOld = table1.getValueAt(fila, 1).toString();
                String autorOld = table1.getValueAt(fila, 2).toString();
                String anioOld = table1.getValueAt(fila, 3).toString();

                String tituloNew = JOptionPane.showInputDialog("editar titulo", tituloOld);
                String autorNew = JOptionPane.showInputDialog("editar autor", autorOld);
                String anioNew = JOptionPane.showInputDialog("editar año", anioOld);

                if (tituloNew.isEmpty()||autorNew.isEmpty()||anioNew.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "Campos vacios"
                    );
                    return;
                }
                try {
                    int NuevoAnio = Integer.parseInt(anioNew);

                    Connection con = conexion.conectar();
                    String sql = "update libros set titulo = ?, autor = ?, anio = ? where id = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, tituloNew);
                    ps.setString(2, autorNew);
                    ps.setInt(3, NuevoAnio);
                    ps.setInt(4, id);

                    ps.executeUpdate();

                    ps.close();
                    con.close();

                    JOptionPane.showMessageDialog(
                            null,
                            "libro actualizado"
                    );

                    cargar_tabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String texto = textField1.getText();

                if (texto.isEmpty()){
                    cargar_tabla();
                    return;
                }

                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("id");
                modelo.addColumn("titulo");
                modelo.addColumn("autor");
                modelo.addColumn("año");

                table1.setModel(modelo);

                try {
                    Connection con = conexion.conectar();

                    String sql = "select * from libros where titulo like ? or autor like?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, "%" + texto + "%");
                    ps.setString(2, "%" + texto + "%");

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()){
                        Object[] fila = new Object[4];
                        fila[0] = rs.getInt("id");
                        fila[1] = rs.getString("titulo");
                        fila[2] = rs.getString("autor");
                        fila[3] = rs.getInt("anio");

                        modelo.addRow(fila);

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

    private void cargar_tabla(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("titulo");
        modelo.addColumn("autor");
        modelo.addColumn("año");

        table1.setModel(modelo);

        try {
            Connection con = conexion.conectar();

            String sql = "select * from libros";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("titulo");
                fila[2] = rs.getString("autor");
                fila[3] = rs.getInt("anio");

                modelo.addRow(fila);

            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
