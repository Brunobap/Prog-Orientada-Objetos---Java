package questoes9a11;

public class Programador extends Funcionario {

    public String linguagem;

    public String getLinguagem() {
        return linguagem;
    }
    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public Programador(String nome, float salario, String cargo, String linguagem) {
        super(nome, salario, cargo);
        this.linguagem = linguagem;
    }    
}
