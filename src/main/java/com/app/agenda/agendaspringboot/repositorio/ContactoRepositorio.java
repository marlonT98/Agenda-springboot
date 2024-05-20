package com.app.agenda.agendaspringboot.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.agenda.agendaspringboot.modelo.Contacto;

public interface ContactoRepositorio extends JpaRepository< Contacto , Integer >    {
    

}
