package com.cooperativa.cooperativa.control;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cierres")
public class Cierre implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;

    @Column(name = "excedente")
    private Double excedente;

    @Column(name = "total_ingresos")
    private Double totalIngresos;

    @Column(name = "total_gastos")
    private Double totalGastos;

    @Column(name = "total_activos")
    private Double totalActivos;

    @Column(name = "total_pasivos")
    private Double totalPasivos;

    @Column(name = "total_patrimonio")
    private Double totalPatrimonio;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "periodo_id", referencedColumnName = "id")
    private Periodo periodo;

    public Cierre() {
    }

    
    public Cierre(Integer id, Date fechaCierre, Double excedente, Double totalIngresos, Double totalGastos, Double totalActivos, Double totalPasivos, Double totalPatrimonio, String observaciones, Periodo periodo) {
        this.id = id;
        this.fechaCierre = fechaCierre;
        this.excedente = excedente;
        this.totalIngresos = totalIngresos;
        this.totalGastos = totalGastos;
        this.totalActivos = totalActivos;
        this.totalPasivos = totalPasivos;
        this.totalPatrimonio = totalPatrimonio;
        this.observaciones = observaciones;
        this.periodo = periodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Double getExcedente() {
        return excedente;
    }

    public void setExcedente(Double excedente) {
        this.excedente = excedente;
    }

    public Double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Double getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(Double totalGastos) {
        this.totalGastos = totalGastos;
    }

    public Double getTotalActivos() {
        return totalActivos;
    }

    public void setTotalActivos(Double totalActivos) {
        this.totalActivos = totalActivos;
    }

    public Double getTotalPasivos() {
        return totalPasivos;
    }

    public void setTotalPasivos(Double totalPasivos) {
        this.totalPasivos = totalPasivos;
    }

    public Double getTotalPatrimonio() {
        return totalPatrimonio;
    }

    public void setTotalPatrimonio(Double totalPatrimonio) {
        this.totalPatrimonio = totalPatrimonio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
    
    
}
