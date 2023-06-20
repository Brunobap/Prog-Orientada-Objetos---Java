package com.biblioteca.biblioteca.Models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JsonBackReference(value = "livro-reserva")
    @JoinColumn(name = "id_livro")
    private Livro livro;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "usuario-reserva")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "dataReserva")
    private Date dataReserva;

    //#region Getters Setters
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
    public Usuario getUsario() {
        return this.usuario;
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
    public Reserva(long id, Livro livro, Usuario usuario, Date dataReserva) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataReserva = dataReserva;
    }
    //#endregion
}
