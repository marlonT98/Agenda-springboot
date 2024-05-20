package com.app.agenda.agendaspringboot.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(  message = "Debe de ingresar su nombre")
    private String nombre;

    @NotEmpty( message = "Debe ingresar su email")
    @Email
    private String email;

    @NotBlank(message = "Debe ingresar su celular")
    private String celular;

    @DateTimeFormat(iso =ISO.DATE)
    @Past //solo se registraran fechas de registro del pasado no del presente o del futuro
    @NotNull(message = "Debe de ingresar su fecha de nacimiento")
    private LocalDate fechaNacimiento;


    private LocalDateTime fechaRegistro;


    public Contacto() {
    }

    public Contacto(Integer id, String nombre, String email, String celular, LocalDate fechaNacimiento,
            LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    
    //con este metodo lo que haremos es que le asiganara la fecha de hoy a la fecha de registro (automaticamente)
    @PrePersist
    public void asiganarFechaRegistro(){
        this.fechaRegistro = LocalDateTime.now();
    }

}
