package br.edu.ifsuldeminas.geometria;

import br.edu.ifsuldeminas.financeiro.Pagamentos;

public class NaoHerda {
	public void teste() {
		// Não é possível iniciar uma geometria, pois ela é abstrata
		//Geometria g = new Geometria();
		
		// pode acessar o método pois é do mesmo pacote, mesmo não herdando
		//g.auxiliar();
		
		Pagamentos pag = new Pagamentos();
		pag = new Pagamentos(10);
	}
	
	
}
