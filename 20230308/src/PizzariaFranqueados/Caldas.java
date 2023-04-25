package PizzariaFranqueados;

import java.util.*;

public class Caldas extends Pizzaria {
	
	@Override
	public List<String> obterIngredientes() {
		List<String> ingredientes = getIngredientes();
		ingredientes.add("Manjeric�o");
		ingredientes.add("Queijo");
		ingredientes.add("Cebola");
		return ingredientes;
	}

	@Override
	protected int obterTempoDeForno() {
		return 60;
	}

}
