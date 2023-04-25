package ListaImplement;

public class MainLista {

	public static void main(String[] args) {
		Lista lista = new Lista(2);
		System.out.println("Tamanho da lista: " + lista.contar());
		
		lista.adicionar("A");
		System.out.println("Tamanho da lista: " + lista.contar());
		lista.adicionar("B");
		System.out.println("Tamanho da lista: " + lista.contar());
		
		System.out.println("\nPosi��o 0 - " + lista.obter(0));
		System.out.println("Posi��o 1 - " + lista.obter(1));
		System.out.println("Posi��o 2 - " + lista.obter(2));
		
		System.out.println("\nElemento removido da posi��o 0 - " + lista.remove(0));
		
		lista.adicionar("C");
		System.out.println("\nPosi��o 0 - " + lista.obter(0));
		
	}
}
