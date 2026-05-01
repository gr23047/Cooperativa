package com.cooperativa.cooperativa.control;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "asientos")
public class Asiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
     @Column(name = "numero")
    private String numero;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "total_debe")
    private Double totalDebe;

    @Column(name = "total_haber")
    private Double totalHaber;

    @Column(name = "estado")
    private String estado;

    @Column(name = "creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;

    @ManyToOne
    @JoinColumn(name = "periodo_id", referencedColumnName = "id")
    private Periodo periodo;

    @OneToMany(mappedBy = "asiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partidas;

    public Asiento() {
    }

    public Asiento(Integer id, String numero, Date fecha, String concepto, String tipo, Double totalDebe, Double totalHaber, String estado, Date creadoEn, Periodo periodo, List<Partida> partidas) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.concepto = concepto;
        this.tipo = tipo;
        this.totalDebe = totalDebe;
        this.totalHaber = totalHaber;
        this.estado = estado;
        this.creadoEn = creadoEn;
        this.periodo = periodo;
        this.partidas = partidas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(Double totalDebe) {
        this.totalDebe = totalDebe;
    }

    public Double getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(Double totalHaber) {
        this.totalHaber = totalHaber;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
    
    
    
    
 
}
