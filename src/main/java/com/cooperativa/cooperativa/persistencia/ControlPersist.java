
package com.cooperativa.cooperativa.persistencia;

import com.cooperativa.cooperativa.control.Cuenta;


public class ControlPersist {
    CuentaJpaController cuentaJPA=new CuentaJpaController();
    
    public void crearCuenta(Cuenta cuenta){
        cuentaJPA.create(cuenta);
    }
}
