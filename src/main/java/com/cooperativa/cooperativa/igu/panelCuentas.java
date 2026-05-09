/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.cooperativa.cooperativa.igu;
import com.cooperativa.cooperativa.servicio.CuentaServicio;
import com.cooperativa.cooperativa.control.Cuenta;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MINEDUCYT
 */

public class panelCuentas extends javax.swing.JPanel {
DefaultTableModel modelo = new DefaultTableModel();
CuentaServicio control = new CuentaServicio();

public panelCuentas() {
    initComponents();
    personalizarTabla();
    String encabezados[] = {
        "Id", "Codigo", "Nombre", "Tipo",
        "Naturaleza", "Clasificacion", "Nivel", "Activa"
    };

    modelo.setColumnIdentifiers(encabezados);
    tblCuentas.setModel(modelo);

    cargarCuentas(); // si tienes este método
}
    public void cargarCuentas() {
        List<Cuenta> cuentas = control.verCuentas();
        for (Cuenta cuenta : cuentas) {
            modelo.addRow(new Object[]{
                cuenta.getId(), cuenta.getCodigo(), cuenta.getNombre(), cuenta.getTipo(),
                cuenta.getNaturaleza(), cuenta.getClasificacion(), cuenta.getNivel(), cuenta.getActiva()
            });
            tblCuentas.setModel(modelo);
        }
    }
   
    public void llenarTabla(List<Cuenta> datos, DefaultTableModel modelo) {
    for (Cuenta cuenta : datos) {
        modelo.addRow(new Object[]{
            cuenta.getId(),
            cuenta.getCodigo(),
            cuenta.getNombre(),
            cuenta.getTipo(),
            cuenta.getNaturaleza(),
            cuenta.getClasificacion(),
            cuenta.getNivel(),
            cuenta.getActiva()
        });
    }

    tblCuentas.setModel(modelo);
}
    // Renderizador personalizado para el efecto cebra para que se vea bien bonito
    private void personalizarTabla() {
        java.awt.Color verdeFila = new java.awt.Color(240, 250, 245); // Un verde menta muy suave
        java.awt.Color blanco = java.awt.Color.WHITE;
        java.awt.Color verdeTexto = new java.awt.Color(3, 102, 102); // Verde oscuro para el texto 
        java.awt.Font poppins = new java.awt.Font("Poppins", java.awt.Font.PLAIN, 13);

        tblCuentas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Si la fila es par, ponemos verde, si es impar, blanco
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? verdeFila : blanco);
                    c.setForeground(verdeTexto);
                } else {
                    c.setBackground(new java.awt.Color(136, 212, 171)); // Verde aqua al seleccionar 
                    c.setForeground(java.awt.Color.BLACK);
                }

                c.setFont(poppins);
                return c;
            }
        });
        // Personalizar el encabezado (Header)
        tblCuentas.getTableHeader().setFont(new java.awt.Font("Poppins", java.awt.Font.BOLD, 14));
        tblCuentas.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblCuentas.setRowHeight(30);
        tblCuentas.getTableHeader().setBackground(new java.awt.Color(3, 102, 102));// Filas más altas para que respire el diseño
        tblCuentas.getTableHeader().setOpaque(true);
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo = new javax.swing.ButtonGroup();
        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelContenedorCuentas = new javax.swing.JPanel();
        panelfiltros = new javax.swing.JPanel();
        rbtnActivo = new javax.swing.JRadioButton();
        rbtnPasivo = new javax.swing.JRadioButton();
        rbtnPatrimonio = new javax.swing.JRadioButton();
        rbtnIngreso = new javax.swing.JRadioButton();
        rbtnGasto = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCuentas = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1270, 720));
        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(131, 189, 116));
        panelTitulo.setPreferredSize(new java.awt.Dimension(1270, 60));
        panelTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Catálogo de Cuentas");
        panelTitulo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 500, 35));

        add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelContenedorCuentas.setBackground(new java.awt.Color(255, 255, 255));
        panelContenedorCuentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 100, 60));
        panelContenedorCuentas.setLayout(new java.awt.BorderLayout());

        panelfiltros.setBackground(new java.awt.Color(255, 255, 255));
        panelfiltros.setAlignmentX(10.0F);

        grupo.add(rbtnActivo);
        rbtnActivo.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        rbtnActivo.setText("Activo");
        rbtnActivo.setMargin(new java.awt.Insets(2, 10, 2, 10));
        rbtnActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnActivoActionPerformed(evt);
            }
        });
        panelfiltros.add(rbtnActivo);

        grupo.add(rbtnPasivo);
        rbtnPasivo.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        rbtnPasivo.setText("Pasivo");
        rbtnPasivo.setMargin(new java.awt.Insets(2, 10, 2, 10));
        rbtnPasivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPasivoActionPerformed(evt);
            }
        });
        panelfiltros.add(rbtnPasivo);

        grupo.add(rbtnPatrimonio);
        rbtnPatrimonio.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        rbtnPatrimonio.setText("Patrimonio");
        rbtnPatrimonio.setMargin(new java.awt.Insets(2, 10, 2, 10));
        rbtnPatrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPatrimonioActionPerformed(evt);
            }
        });
        panelfiltros.add(rbtnPatrimonio);

        grupo.add(rbtnIngreso);
        rbtnIngreso.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        rbtnIngreso.setText("Ingreso");
        rbtnIngreso.setMargin(new java.awt.Insets(2, 10, 2, 10));
        rbtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnIngresoActionPerformed(evt);
            }
        });
        panelfiltros.add(rbtnIngreso);

        grupo.add(rbtnGasto);
        rbtnGasto.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        rbtnGasto.setText("Gasto");
        rbtnGasto.setMargin(new java.awt.Insets(2, 10, 2, 10));
        rbtnGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnGastoActionPerformed(evt);
            }
        });
        panelfiltros.add(rbtnGasto);

        panelContenedorCuentas.add(panelfiltros, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 10, 1));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 480));

        tblCuentas.setForeground(new java.awt.Color(3, 102, 102));
        tblCuentas.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tblCuentas);

        panelContenedorCuentas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panelContenedorCuentas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnPatrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPatrimonioActionPerformed
        modelo.setRowCount(0);
        List<Cuenta> patrimonio = control.cuentasTipo("Patrimonio");
        llenarTabla(patrimonio, modelo);

    }//GEN-LAST:event_rbtnPatrimonioActionPerformed

    private void rbtnGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnGastoActionPerformed
        modelo.setRowCount(0);
        List<Cuenta> gasto = control.cuentasTipo("Gasto");
        llenarTabla(gasto, modelo);
    }//GEN-LAST:event_rbtnGastoActionPerformed

    private void rbtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnIngresoActionPerformed
        modelo.setRowCount(0);
        List<Cuenta> ingreso = control.cuentasTipo("Ingreso");
        llenarTabla(ingreso, modelo);
    }//GEN-LAST:event_rbtnIngresoActionPerformed

    private void rbtnActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnActivoActionPerformed
        modelo.setRowCount(0);
        List<Cuenta> activo = control.cuentasTipo("Activo");
        llenarTabla(activo, modelo);
    }//GEN-LAST:event_rbtnActivoActionPerformed

    private void rbtnPasivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPasivoActionPerformed
        modelo.setRowCount(0);
        List<Cuenta> pasivo = control.cuentasTipo("Pasivo");
        llenarTabla(pasivo, modelo);
    }//GEN-LAST:event_rbtnPasivoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelContenedorCuentas;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JPanel panelfiltros;
    private javax.swing.JRadioButton rbtnActivo;
    private javax.swing.JRadioButton rbtnGasto;
    private javax.swing.JRadioButton rbtnIngreso;
    private javax.swing.JRadioButton rbtnPasivo;
    private javax.swing.JRadioButton rbtnPatrimonio;
    private javax.swing.JTable tblCuentas;
    // End of variables declaration//GEN-END:variables
}
