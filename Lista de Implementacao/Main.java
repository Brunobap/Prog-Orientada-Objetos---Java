import questoes1a3.Cachorro;
import questoes1a3.Gato;
import questoes4e5.*;
import questoes6a8.*;
import questoes9a11.*;
import questoes12a14.*;

public class Main {
    public static void main(String[] args) {
        Cachorro cachorro = new Cachorro("Dick");
        cachorro.emitirSom();

        Gato gato = new Gato("Pantera");
        gato.emitirSom();

        Pessoa fulano = new Pessoa("Fulano da Silva", 54);
        Aluno aluno = new Aluno("Bruno Batista", 20, "202111020022");

        Veiculo veiculo = new Veiculo("Caloi", "Bicicleta");
        Carro carro = new Carro("Ford", "Mustang", 4);
        Moto moto = new Moto("Suzuki", "Motocross",600);

        Funcionario funcionario = new Funcionario("Manuel", 1500, "Zelador");
        Gerente gerente = new Gerente("Gabriel", 5000, "Gerente de Sessão", "RH");
        Programador programador = new Programador("Samuel", 1200, "Desenvolvedor", "Java");

        Animal animal = new Animal();
        animal.mover();
        Ave ave = new Ave();
        ave.mover();
        Peixe peixe = new Peixe();
        peixe.mover();

        System.out.println();
    }
}
