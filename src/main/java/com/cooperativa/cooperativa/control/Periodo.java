package com.cooperativa.cooperativa.control;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "periodos")
public class Periodo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "cerrado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerradoEn;

    @OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL)
    private List<Asiento> asientos;

    @OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL)
    private List<Cierre> cierres;

    public Periodo() {
    }

    
    public Periodo(Integer id, String nombre, Date fechaInicio, Date fechaFin, String estado, String observaciones, Date cerradoEn, List<Asiento> asientos, List<Cierre> cierres) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.observaciones = observaciones;
        this.cerradoEn = cerradoEn;
        this.asientos = asientos;
        this.cierres = cierres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getCerradoEn() {
        return cerradoEn;
    }

    public void setCerradoEn(Date cerradoEn) {
        this.cerradoEn = cerradoEn;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public List<Cierre> getCierres() {
        return cierres;
    }

    public void setCierres(List<Cierre> cierres) {
        this.cierres = cierres;
    }
    
    
}
