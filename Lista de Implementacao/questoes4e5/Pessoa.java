package questoes4e5;

public class Pessoa {
    public String nome;
    public int idade;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Pessoa(String nome, int idade) {
        this.idade = idade;
        this.nome = nome;
    }
}
