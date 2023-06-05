package com.exercicios.exercicios.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "disciplina")
public class Disciplina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "disciplina", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Aluno> alunos;
    
    @Column(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }
        
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    // um método "adicionarAluno(aluno)" para incluir o aluno na disciplina, verificando se o aluno já existe utilizando a "matrícula";
    public boolean adicionarAluno(Aluno aluno){
        for (Aluno a : alunos){
            if (a.getMatricula().equals(aluno.getMatricula())) return false;
        }
        this.alunos.add(aluno);
        return true;
    }

    // um método "removerAluno(nome)" que retira o aluno da disciplina procurando pelo nome.
    public Aluno removerAluno(String nome) {
        for (Aluno a : alunos){
            if (a.getNome().equals(nome)) {
                this.alunos.remove(a);
                return a;
            }
        }
        return null;
    }

    // um método "listarAlunos()" que apresenta uma lista de alunos separada por status (Aprovado ou Reprovado) com a média obtida por cada aluno. A nota de aprovação deve ser maior ou igual a 6.
    public List<Aluno> listaAlunos() {
        return null;
    }

    // Post para criar uma disciplina informando o nome da disciplina;
    // Post para informar o Id do aluno a ser cadastrado na disciplina;
    // Delete para informar o Id do aluno a ser removido da disciplina;
    // Get para recuperar todas as disciplinas cadastradas;
    // Put para editar o nome da disciplina;
    // Get para listar os alunos separados por status (aprovado ou reprovado).

}
