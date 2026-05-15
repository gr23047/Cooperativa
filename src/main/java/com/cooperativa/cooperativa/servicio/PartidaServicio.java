package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.persistencia.PartidaJpaController;
import java.util.List;

public class PartidaServicio {
    PartidaJpaController partidaJPA=new PartidaJpaController();
    
    public void crearPartida(Partida partida){
        partidaJPA.create(partida);
    }

    public List<Object[]> getMayorizado(Integer id) {
        return partidaJPA.getMayorizado(id);
    }
    
}
