
package com.cooperativa.cooperativa;

import com.cooperativa.cooperativa.control.Controladora;
import com.cooperativa.cooperativa.control.Cuenta;

public class Cooperativa {
    public static void main(String[] args) {
        Controladora control=new Controladora();
        Cuenta cuenta=new Cuenta();
        cuenta.setNombre("IVA");
        cuenta.setTipo("Gasto");
        cuenta.setNaturaleza("Deudora");
        cuenta.setClasificacion("Balance");
        cuenta.setNivel(4);
        cuenta.setActiva(true);
        
        control.crearCuenta(cuenta);
    }
}
