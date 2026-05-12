/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.cooperativa.cooperativa.igu;

import com.cooperativa.cooperativa.control.Asiento;
import com.cooperativa.cooperativa.control.Cuenta;
import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.servicio.AsientoServicio;
import com.cooperativa.cooperativa.servicio.CuentaServicio;
import com.cooperativa.cooperativa.servicio.PartidaServicio;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MINEDUCYT
 */
public class panelPartidas extends javax.swing.JPanel {
     LocalDate hoy = LocalDate.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaFormateada = hoy.format(formato);
    Date fecha = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
    CuentaServicio cuentaserv = new CuentaServicio();
    PartidaServicio partidaserv = new PartidaServicio();
    AsientoServicio asientoserv = new AsientoServicio();
    List<Partida> listaTemporalPartidas = new ArrayList<>();

    DefaultTableModel modelo = new DefaultTableModel();
    String[] encabezados = {
        "Codigo", "Cuenta", "Parcial", "Debe", "Haber"
    };

    public boolean isCellEditable(int row, int column) {
        // Solo las columnas 2 (Debe) y 3 (Haber) se pueden editar
        return column == 2 || column == 3 || column == 4;
    }

    Asiento asiento = new Asiento();
    Double totalDebe = 0.00;
    Double totalHaber = 0.00;

    /**
     * Creates new form panelPartidas
     */
    public panelPartidas() {
        initComponents();

        tblPartidas.setModel(modelo);

        // AGREGAR EL LISTENER AQUÍ:
        modelo.addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                // Si el cambio fue una edición en una celda
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                    recalcularTotales();
                }
            }
        });

        ftxtFecha.setText(fechaFormateada);
        traerCuentas();

        modelo.setColumnIdentifiers(encabezados);
        tblPartidas.setModel(modelo);
    }

    public void traerCuentas() {
        List<Cuenta> cuentas = cuentaserv.cuentasCodigo();
        for (Cuenta cuenta : cuentas) {
            cmb_Cuentas.addItem(cuenta.getCodigo() + "-" + cuenta.getNombre());
        }
    }

    public void traersubCuentas() {
        String primerosCuatro = "";
        Object seleccionado = cmb_Cuentas.getSelectedItem();

        if (seleccionado != null) {
            String textoCompleto = seleccionado.toString();

            // 2. Verificamos que tenga al menos 4 caracteres para evitar errores
            if (textoCompleto.length() >= 4) {
                primerosCuatro = textoCompleto.substring(0, 4);
            }
        }

        cmbSubCuentas.removeAllItems();
        List<Cuenta> cuentas = cuentaserv.subcuentasCodigo(primerosCuatro);
        for (Cuenta cuenta : cuentas) {
            cmbSubCuentas.addItem(cuenta.getCodigo() + "-" + cuenta.getNombre());
        }
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (Partida partida : listaTemporalPartidas) {
            modelo.addRow(new Object[]{
                partida.getCuenta().getCodigo(),
                partida.getCuenta().getNombre(),
                partida.getParcial(),
                partida.getDebe(),
                partida.getHaber()
            });
            tblPartidas.setModel(modelo);
            recalcularTotales();
        }

    }

    private void recalcularTotales() {
        double sumaDebe = 0;
        double sumaHaber = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                // Obtenemos los valores de las columnas 2 y 3
                Object valorDebe = modelo.getValueAt(i, 3);
                Object valorHaber = modelo.getValueAt(i, 4);

                sumaDebe += (valorDebe != null) ? Double.parseDouble(valorDebe.toString()) : 0;
                sumaHaber += (valorHaber != null) ? Double.parseDouble(valorHaber.toString()) : 0;
            } catch (NumberFormatException ex) {
                // Si el usuario escribe algo que no es un número, ignoramos o avisamos
            }
        }

        // Actualizamos tus labels
        lblTotalDebe.setText("Total Debe: $" + sumaDebe);
        lblTotalHaber.setText("Total Haber: $" + sumaHaber);

        // Si usas la variable global totalDebe, actualízala también
        this.totalDebe = sumaDebe;
         this.totalHaber = sumaHaber;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        panelInputs = new javax.swing.JPanel();
        lblNumeroAsiento = new javax.swing.JLabel();
        txtNumeroAsiento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ftxtFecha = new javax.swing.JFormattedTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmb_Cuentas = new javax.swing.JComboBox<>();
        btnDebe = new javax.swing.JButton();
        btnHaber = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmbSubCuentas = new javax.swing.JComboBox<>();
        btnparcial = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPartidas = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        lblTotalDebe = new javax.swing.JLabel();
        lblTotalHaber = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1270, 60));
        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(131, 189, 116));
        panelTitulo.setPreferredSize(new java.awt.Dimension(1270, 60));
        panelTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Partidas Diarias");
        panelTitulo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 240, 35));

        add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelContenido.setBackground(new java.awt.Color(255, 255, 255));
        panelContenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelContenido.setLayout(new java.awt.BorderLayout());

        panelInputs.setBackground(new java.awt.Color(255, 255, 255));
        panelInputs.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 50, 10, 40));
        panelInputs.setPreferredSize(new java.awt.Dimension(1200, 150));
        panelInputs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNumeroAsiento.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblNumeroAsiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumeroAsiento.setText("N# Asiento");
        panelInputs.add(lblNumeroAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        txtNumeroAsiento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroAsiento.setPreferredSize(new java.awt.Dimension(100, 28));
        txtNumeroAsiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroAsientoActionPerformed(evt);
            }
        });
        panelInputs.add(txtNumeroAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 64, 22));

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha");
        panelInputs.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        try {
            ftxtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtFecha.setPreferredSize(new java.awt.Dimension(120, 28));
        ftxtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtFechaActionPerformed(evt);
            }
        });
        panelInputs.add(ftxtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 64, 22));

        lblCantidad.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidad.setText("Cantidad");
        panelInputs.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        txtCantidad.setPreferredSize(new java.awt.Dimension(100, 28));
        panelInputs.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 64, 22));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tipo");
        panelInputs.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, -1));

        cmbTipo.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apertura", "Normal", "Cierre" }));
        cmbTipo.setMinimumSize(new java.awt.Dimension(150, 28));
        panelInputs.add(cmbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 150, 28));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("sub cuenta");
        panelInputs.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, -1, -1));

        cmb_Cuentas.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        cmb_Cuentas.setPreferredSize(new java.awt.Dimension(250, 28));
        cmb_Cuentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_CuentasItemStateChanged(evt);
            }
        });
        cmb_Cuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_CuentasMouseClicked(evt);
            }
        });
        cmb_Cuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_CuentasActionPerformed(evt);
            }
        });
        panelInputs.add(cmb_Cuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 190, 24));

        btnDebe.setBackground(new java.awt.Color(99, 156, 84));
        btnDebe.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        btnDebe.setForeground(new java.awt.Color(255, 255, 255));
        btnDebe.setText("Debe");
        btnDebe.setBorderPainted(false);
        btnDebe.setPreferredSize(new java.awt.Dimension(100, 30));
        btnDebe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDebeActionPerformed(evt);
            }
        });
        panelInputs.add(btnDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 72, 25));

        btnHaber.setBackground(new java.awt.Color(99, 156, 84));
        btnHaber.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        btnHaber.setForeground(new java.awt.Color(255, 255, 255));
        btnHaber.setText("Haber");
        btnHaber.setBorderPainted(false);
        btnHaber.setPreferredSize(new java.awt.Dimension(100, 30));
        btnHaber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHaberActionPerformed(evt);
            }
        });
        panelInputs.add(btnHaber, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 72, 25));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Cuenta");
        panelInputs.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        cmbSubCuentas.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        cmbSubCuentas.setPreferredSize(new java.awt.Dimension(250, 28));
        cmbSubCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSubCuentasActionPerformed(evt);
            }
        });
        panelInputs.add(cmbSubCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 220, 24));

        btnparcial.setBackground(new java.awt.Color(99, 156, 84));
        btnparcial.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        btnparcial.setForeground(new java.awt.Color(255, 255, 255));
        btnparcial.setText("Parcial");
        btnparcial.setBorderPainted(false);
        btnparcial.setPreferredSize(new java.awt.Dimension(100, 30));
        btnparcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnparcialActionPerformed(evt);
            }
        });
        panelInputs.add(btnparcial, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 72, 25));

        panelContenido.add(panelInputs, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(tblPartidas);

        panelContenido.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelBotones.setBackground(new java.awt.Color(255, 255, 255));
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 5));

        lblTotalDebe.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        panelBotones.add(lblTotalDebe);

        lblTotalHaber.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        panelBotones.add(lblTotalHaber);

        btnGuardar.setBackground(new java.awt.Color(0, 102, 102));
        btnGuardar.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/garrapata.png"))); // NOI18N
        btnGuardar.setText("Guardar Asiento");
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelBotones.add(btnGuardar);

        panelContenido.add(panelBotones, java.awt.BorderLayout.SOUTH);

        add(panelContenido, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

 
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        asiento.setNumero(txtNumeroAsiento.getText());
        asiento.setFecha(fecha);
        asiento.setCreadoEn(fecha);
        asiento.setTipo(cmbTipo.getSelectedItem().toString());
        String concepto = JOptionPane.showInputDialog("Ingrese el conepto: ");
        asiento.setConcepto(concepto);
        asiento.setEstado("Registrado");

        // 1. Validar Partida Doble
        if (totalDebe.equals(totalHaber)) {
            asiento.setTotalDebe(totalDebe);
            asiento.setTotalHaber(totalHaber);
            // 2. Persistir el Asiento Principal
            asientoserv.crearAsiento(asiento);

            // 3. Persistir cada Partida de la lista
            for (Partida p : listaTemporalPartidas) {
                p.setAsiento(asiento);
                partidaserv.crearPartida(p);
            }

            JOptionPane.showMessageDialog(this, "Asiento contable guardado con éxito.");
            // limpiarFormulario(); // Método para resetear todo

        } else {
            // El terror de los contadores: El asiento no cuadra
            Double diferencia = Math.abs(totalDebe - totalHaber);
            JOptionPane.showMessageDialog(this,
                "El asiento no cuadra. Diferencia: " + diferencia,
                "Error de Cuadre",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNumeroAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroAsientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroAsientoActionPerformed

    private void ftxtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtFechaActionPerformed

    private void cmb_CuentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_CuentasItemStateChanged
        // TODO add your handling code here:
       this.traersubCuentas();
    }//GEN-LAST:event_cmb_CuentasItemStateChanged

    private void cmb_CuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_CuentasMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_CuentasMouseClicked

    private void cmb_CuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_CuentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_CuentasActionPerformed

    private void btnDebeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDebeActionPerformed
        Partida partida = new Partida();
        Asiento asiento = new Asiento();
        Double debe = Double.parseDouble(txtCantidad.getText());

        partida.setDebe(debe);
        partida.setHaber(0.0);
        partida.setAsiento(asiento);
        String cuentaSeleccionada = cmb_Cuentas.getSelectedItem().toString();
        String[] partes = cuentaSeleccionada.split("-");
        String codigo = partes[0];
        partida.setCuenta(cuentaserv.buscarPorCodigo(codigo));
        listaTemporalPartidas.add(partida);

        this.recalcularTotales();
        JOptionPane.showMessageDialog(this, "Partida agregada al debe (Temporal)");
        actualizarTabla();
    }//GEN-LAST:event_btnDebeActionPerformed

    private void btnHaberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHaberActionPerformed
        Partida partida = new Partida();
        Asiento asiento = new Asiento();
        Double haber = Double.parseDouble(txtCantidad.getText());
        partida.setAsiento(asiento);

        partida.setHaber(haber);
        partida.setDebe(0.0);
        String cuentaSeleccionada = cmb_Cuentas.getSelectedItem().toString();
        String[] partes = cuentaSeleccionada.split("-");
        String codigo = partes[0];
        partida.setCuenta(cuentaserv.buscarPorCodigo(codigo));
        listaTemporalPartidas.add(partida);

        this.recalcularTotales();
        JOptionPane.showMessageDialog(this, "Partida agregada al haber (Temporal)");
        actualizarTabla();
    }//GEN-LAST:event_btnHaberActionPerformed

    private void cmbSubCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSubCuentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSubCuentasActionPerformed

    private void btnparcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnparcialActionPerformed
        Partida partida = new Partida();
        Asiento asiento = new Asiento();
        Double parcial = Double.parseDouble(txtCantidad.getText());

        partida.setParcial(parcial);
        partida.setDebe(0.0);
        partida.setHaber(0.0);
        partida.setAsiento(asiento);
        String cuentaSeleccionada = cmbSubCuentas.getSelectedItem().toString();

        // Verificamos que tenga el largo mínimo para no dar error
        if (cuentaSeleccionada.length() >= 7) {
            String codigoExacto = cuentaSeleccionada.substring(0, 7); // Corta exactamente "1111-11"

            // Ahora buscamos en la base de datos con el código completo
            Cuenta subCuenta = cuentaserv.buscarSubCuenta(codigoExacto);
            partida.setCuenta(subCuenta);
            listaTemporalPartidas.add(partida);

            this.recalcularTotales();
            JOptionPane.showMessageDialog(this, "Partida agregada al debe (Temporal)");
            actualizarTabla();
        }
    }//GEN-LAST:event_btnparcialActionPerformed

    
    
   
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDebe;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHaber;
    private javax.swing.JButton btnparcial;
    private javax.swing.JComboBox<String> cmbSubCuentas;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JComboBox<String> cmb_Cuentas;
    private javax.swing.JFormattedTextField ftxtFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblNumeroAsiento;
    private javax.swing.JLabel lblTotalDebe;
    private javax.swing.JLabel lblTotalHaber;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelInputs;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTable tblPartidas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNumeroAsiento;
    // End of variables declaration//GEN-END:variables
}
