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
@Table(name = "Emprestimo")
public class Emprestimo {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Livro livro;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Biblioteca biblioteca;

    @Column(name = "dataEmprestimo")
    private Date dataEmprestimo;

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
    public Date getDataEmprestimo() {
        return this.dataEmprestimo;
    }
    public void setDataReserva(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }  
    //#endregion

    //#region ctor's
    public Emprestimo() {
        // ctor vazio, feito pro SpringBoot não reclamar
    }
    public Emprestimo(Livro livro, Usuario usuario, Biblioteca biblioteca, Date dataEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.biblioteca = biblioteca;
        this.dataEmprestimo = dataEmprestimo;
    }
    //#endregion
}
