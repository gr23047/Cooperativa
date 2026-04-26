package com.cooperativa.cooperativa.control;

import com.cooperativa.cooperativa.persistencia.ControlPersist;

import java.util.List;

public class Controladora {

    ControlPersist controlPersist = new ControlPersist();

    public void crearCuenta(Cuenta cuenta) {
        controlPersist.crearCuenta(cuenta);
    }

    public List<Cuenta> verCuentas() {
        return controlPersist.verCuentas();
    }

    public List<Cuenta> cuentasTipo(String tipo) {
        return controlPersist.cuentasTipo(tipo);
    }
}
