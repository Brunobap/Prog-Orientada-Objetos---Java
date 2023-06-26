package com.biblioteca.biblioteca.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Biblioteca")
public class Biblioteca {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "nome", unique = true)
    private String nome;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Reserva> reservas;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Emprestimo> emprestimos;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Livro> livros;
    
    //#region Getters Setters
    public List<Livro> getLivros() {
        return livros;
    }
    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
    public List<Emprestimo> getEmprestimo() {
        return emprestimos;
    }
    public void setEmprestimo(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
    public List<Reserva> getReserva() {
        return reservas;
    }
    public void setReserva(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    //#endregion

    //#region ctor's
    public Biblioteca() {
        // ctor vazio, feito pro SpringBoot não reclamar
    }
    public Biblioteca(long id, String nome, List<Emprestimo> emprestimos, List<Reserva> reservas, List<Livro> livros) {
        this.id = id;
        this.nome = nome;
        this.emprestimos = emprestimos;
        this.reservas = reservas;
        this.livros = livros;
    }
    //#endregion
}
