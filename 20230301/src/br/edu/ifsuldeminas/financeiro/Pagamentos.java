package br.edu.ifsuldeminas.financeiro;

import br.edu.ifsuldeminas.geometria.Geometria;

public class Pagamentos {// extends Geometria{
	
	public int n1;
	private int n2;
	private int n3;
	
	// construtor padr�o, j� vem escondido em toda classe sem construtor presente
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
		// N�o pode inicializar uma geometria, ela � abstrata
		//Geometria g = new Geometria();
		
		// N�o pode acessar o m�todo pois � de outro pacote, mesmo herdando dele
		//g.auxiliar();
	}
}
