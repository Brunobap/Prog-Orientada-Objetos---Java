package Pizzaria;

import PizzariaFranqueados.*;

public class MainPizzaria {
	
	public static void main(String[] args) {
		PocosCaldas pocos = new PocosCaldas();
		Caldas caldas = new Caldas();
		Botelhos botelhos = new Botelhos();
		
		System.out.println("**Processando Poços de Caldas**");
		pocos.realizarProcesso();

		System.out.println("\n\n**Processando Caldas**");
		caldas.realizarProcesso();

		System.out.println("\n\n**Processando Botelhos**");
		botelhos.realizarProcesso();
	}
}
