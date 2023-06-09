package com.biblioteca.biblioteca.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Usuario")
public class Usuario {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "RA")
    private String RA;
    
    @Column(name = "nome", unique = true)
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Reserva> reservas;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Emprestimo> emprestimos;

    //#region Getters Setters
    public List<Emprestimo> getEmprestimo() {
        return emprestimos;
    }
    public void setEmprestimo(List<Emprestimo> emprestimo) {
        this.emprestimos = emprestimo;
    }
    public List<Reserva> getReserva() {
        return reservas;
    }
    public void setReserva(List<Reserva> reserva) {
        this.reservas = reserva;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getRA() {
        return RA;
    }
    public void setRA(String RA) {
        this.RA = RA;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }  
    //#endregion

    //#region ctor's
    public Usuario() {
        // ctor vazio, feito pro SpringBoot não reclamar
    }
    public Usuario(long id, String ra, String nome, String email) {
        this.id = id;
        this.RA = ra;
        this.nome = nome;
        this.email = email;
    }
    //#endregion
}
