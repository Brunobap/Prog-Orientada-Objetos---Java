import ListImplement.ListaVetor;

public class Main {
    public static void main(String[] args) {
        ListaVetor vet = new ListaVetor();
        vet.adicionar("A");
        vet.adicionar("B");
        vet.adicionar("C");
        vet.adicionar("D");

        vet.remove(1);
    }
}
