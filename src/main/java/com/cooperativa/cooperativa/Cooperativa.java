package com.cooperativa.cooperativa;

import com.cooperativa.cooperativa.control.Asiento;
import com.cooperativa.cooperativa.servicio.AsientoServicio;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Cooperativa {

    public static void main(String[] args) {
        LocalDate hoy = LocalDate.now();
        Date fecha = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
        AsientoServicio asientoservicio = new AsientoServicio();
        Asiento asiento1 = new Asiento();
        asiento1.setNumero("A001");
        asiento1.setFecha(fecha);
        asiento1.setConcepto("Apertura de cooperativa xd");
        asiento1.setTipo("Apertura");
//        asiento1.setPeriodo();
        asiento1.setTotalDebe(1500.00);
        asiento1.setTotalHaber(1500.00);
        asiento1.setCreadoEn(fecha);
        asiento1.setEstado("Registrado");
        asientoservicio.crearAsiento(asiento1);

    }
}
