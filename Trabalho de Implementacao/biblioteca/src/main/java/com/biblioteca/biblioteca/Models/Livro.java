package com.biblioteca.biblioteca.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Livro")
public class Livro {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "autor")
    private String autor;

    @Column(name = "anopublicacao")
    private Integer anopublicacao;

    @Column(name = "disponivel")
    private Boolean disponivel;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return this.autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public Integer getAnoPublicacao() {
        return this.anopublicacao;
    }
    public void setAnoPublicacao(Integer anopublicacao) {
        this.anopublicacao = anopublicacao;
    }  
    public Boolean getDisponivel() {
        return this.disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }  

    public Livro() {
        // ctor vazio, feito pro SpringBoot não reclamar
    }

    public Livro(long id, String titulo, String autor, Integer anopublicacao, Boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anopublicacao = anopublicacao;
        this.disponivel = disponivel;
    }
}
