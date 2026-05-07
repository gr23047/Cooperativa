package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.persistencia.PartidaJpaController;

public class PartidaServicio {
    PartidaJpaController partidaJPA=new PartidaJpaController();
    
    public void crearPartida(Partida partida){
        partidaJPA.create(partida);
    }
    
}
