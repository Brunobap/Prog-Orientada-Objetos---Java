package br.edu.ifsuldeminas.geometria;

import br.edu.ifsuldeminas.financeiro.Pagamentos;

public class NaoHerda {
	public void teste() {
		// N�o � poss�vel iniciar uma geometria, pois ela � abstrata
		//Geometria g = new Geometria();
		
		// pode acessar o m�todo pois � do mesmo pacote, mesmo n�o herdando
		//g.auxiliar();
		
		Pagamentos pag = new Pagamentos();
		pag = new Pagamentos(10);
	}
	
	
}
