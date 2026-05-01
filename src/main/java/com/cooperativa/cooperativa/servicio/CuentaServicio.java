package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Cuenta;
import com.cooperativa.cooperativa.persistencia.CuentaJpaController;

import java.util.List;

public class CuentaServicio {

    CuentaJpaController cuentaJPA= new CuentaJpaController();

    public void crearCuenta(Cuenta cuenta) {
        cuentaJPA.create(cuenta);
    }

    public List<Cuenta> verCuentas() {
        return cuentaJPA.findCuentaEntities();
    }

    public List<Cuenta> cuentasTipo(String tipo) {
        return cuentaJPA.findCuentasByTipo(tipo);
    }
 
}
