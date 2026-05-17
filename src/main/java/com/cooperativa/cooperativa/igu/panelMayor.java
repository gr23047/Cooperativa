/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.cooperativa.cooperativa.igu;

import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.servicio.PartidaServicio;
import com.cooperativa.cooperativa.servicio.PeriodoServicio;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author godofredo
 */
public class panelMayor extends javax.swing.JPanel {

    DefaultTableModel modelo = new DefaultTableModel();
    PeriodoServicio periodoserv = new PeriodoServicio();
    PartidaServicio partidaserv = new PartidaServicio();
    String[] encabezados = {
        "Código", "Cuenta", "Tipo","Naturaleza",
        "Total Debe", "Total Haber", "Saldo"
    };

    public panelMayor() {
        initComponents();
        cargarPeriodos();
        modelo.setColumnIdentifiers(encabezados);
        tblMayor.setModel(modelo);
    }

    public void cargarPeriodos() {
        List<Periodo> periodos = periodoserv.traerPeriodos();
        for (Periodo periodo : periodos) {
            cmbPeriodos.addItem(periodo.getId() + "-" + periodo.getNombre());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        cmbPeriodos = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMayor = new javax.swing.JTable();
        btnMayorizar = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblTitulo.setText("Mayorizacion");

        cmbPeriodos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione el periodo>" }));
        cmbPeriodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPeriodosActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tblMayor);

        btnMayorizar.setText("Mayorizar");
        btnMayorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayorizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbPeriodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnMayorizar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPeriodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMayorizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPeriodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPeriodosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPeriodosActionPerformed

    private void cargarMayor() {
    modelo.setRowCount(0);   // limpiar tabla
    String periodoTexto = cmbPeriodos.getSelectedItem().toString();
    String[] partes=periodoTexto.split("-");
    String id=partes[0];
    if (periodoTexto.contains("<Seleccione el periodo>") ) {
        JOptionPane.showMessageDialog(this, "Selecciona un período.");
        return;
    }

    List<Object[]> filas = partidaserv.getMayorizado(Integer.parseInt(id));

    double sumaDebe  = 0;
    double sumaHaber = 0;

    for (Object[] f : filas) {
        String codigo     = (String) f[0];
        String nombre     = (String) f[1];
        String tipo       = (String) f[2];
        String naturaleza = (String) f[3];
        double debe       = ((Number) f[4]).doubleValue();
        double haber      = ((Number) f[5]).doubleValue();
        double saldo      = ((Number) f[6]).doubleValue();

        sumaDebe  += debe;
        sumaHaber += haber;

        modelo.addRow(new Object[]{
            codigo,
            nombre,
            tipo,
            naturaleza,
            String.format("$%,.2f", debe),
            String.format("$%,.2f", haber),
            String.format("$%,.2f", saldo)
        });
    }

//    lblTotalDebe .setText(String.format("Total Debe:  $%,.2f", sumaDebe));
//    lblTotalHaber.setText(String.format("Total Haber: $%,.2f", sumaHaber));
}
    private void btnMayorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayorizarActionPerformed
        cargarMayor();
    }//GEN-LAST:event_btnMayorizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMayorizar;
    private javax.swing.JComboBox<String> cmbPeriodos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblMayor;
    // End of variables declaration//GEN-END:variables
}
