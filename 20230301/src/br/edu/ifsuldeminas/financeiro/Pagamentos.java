package br.edu.ifsuldeminas.financeiro;

import br.edu.ifsuldeminas.geometria.Geometria;

public class Pagamentos {// extends Geometria{
	
	public int n1;
	private int n2;
	private int n3;
	
	// construtor padrão, já vem escondido em toda classe sem construtor presente
	public Pagamentos() {
		this(100);
	}
	
	// construtor sobrecarregado
	public Pagamentos(int val) {
		n1 = val;
		n2 = val+10;
		n3 = val-10;
	}
	
	public void teste() {
		// Não pode inicializar uma geometria, ela é abstrata
		//Geometria g = new Geometria();
		
		// Não pode acessar o método pois é de outro pacote, mesmo herdando dele
		//g.auxiliar();
	}
}
