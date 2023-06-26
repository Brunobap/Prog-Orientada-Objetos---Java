package com.biblioteca.biblioteca.Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reserva")
public class Reserva {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Livro livro;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Biblioteca biblioteca;

    @Column(name = "dataReserva")
    private Date dataReserva;

    //#region Getters Setters
    public Biblioteca getBiblioteca() {
        return biblioteca;
    }
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Date getDataReserva() {
        return this.dataReserva;
    }
    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }  
    //#endregion

    //#region ctor's
    public Reserva() {
        // ctor vazio, feito pro SpringBoot não reclamar
    }
    public Reserva(Livro livro, Usuario usuario, Biblioteca biblioteca, Date dataReserva) {
        //this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.biblioteca = biblioteca;
        this.dataReserva = dataReserva;
    }
    //#endregion
}
