
package com.cooperativa.cooperativa.persistencia;

import com.cooperativa.cooperativa.control.Cuenta;
import java.util.List;


public class ControlPersist {
    CuentaJpaController cuentaJPA=new CuentaJpaController();
    
    public void crearCuenta(Cuenta cuenta){
        cuentaJPA.create(cuenta);
    }

    public List<Cuenta> verCuentas() {
        return cuentaJPA.findCuentaEntities();
    }
}
