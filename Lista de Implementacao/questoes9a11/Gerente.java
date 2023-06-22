package questoes9a11;

public class Gerente extends Funcionario {

    public String departamento;

    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Gerente(String nome, float salario, String cargo, String departamento) {
        super(nome, salario, cargo);
        this.departamento = departamento;
    }    
}
