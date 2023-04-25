package ListaImplement;

public class Lista {
	
	private String[] lista;
	
	private int obterPosicaoLivre() {
		int livre = -1;
		for(int i=0; i<lista.length; i++) {
			if(lista[i] == null)
				return i;
		}
		return livre;
	}
	
	public Lista(int tamanho) {
		lista = new String[tamanho];
	}

	public void adicionar(String elemento) {
		int indice = obterPosicaoLivre();
		if (indice < 0) {
			System.out.println("Lista cheia!!");
			return;
		}
		lista[indice] = elemento;
	}
	public int contar() {
		int contagem = 0;
		
		for (int i=0; i<lista.length; i++) {
			if (lista[i] != null)
				contagem++;
		}
		
		return contagem;
	}
	
	public String remove(int posicao) {
		if (posicao >= lista.length) {
			System.out.println("Essa posição não existe");
			return "";
		}
		
		String elemento = lista[posicao];
		lista[posicao] = "";
		return elemento;
	}
	
	public String obter(int posicao) {
		if (posicao >= lista.length) {
			System.out.println("Essa posição não existe");
			return "";
		}
		
		return lista[posicao];
	}
}
