package questoes1a3;

public class Cachorro extends Animal {

    public Cachorro(String nome) {
        super(nome); 
    }

    @Override
    public void emitirSom() {
        System.out.println("Au Au");
    }
    
}
