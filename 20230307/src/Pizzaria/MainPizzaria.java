package Pizzaria;

import PizzariaFranqueados.*;

public class MainPizzaria {
	
	public static void main(String[] args) {
		//System.out.println("Ola");
		PocosCaldas pocos = new PocosCaldas();
		pocos.metodoQualquer();
		
		processa(pocos);
	}
	
	public static void processa(Pizzaria pizzaria) {
		if (pizzaria instanceof PocosCaldas) {
			PocosCaldas pocosCaldas = (PocosCaldas) pizzaria;
			pocosCaldas.metodoQualquer();
		}
	}
}
