package PizzariaFranqueados;

import java.util.ArrayList;
import java.util.List;

public class PocosCaldas extends Pizzaria {
	
	public void metodoQualquer() {
		
	}

	@Override
	public List<String> obterIngredientes() {
		List<String> ingredientes = new ArrayList<String>();
		ingredientes.add("Ervilha");
		ingredientes.add("Palmito");
		ingredientes.add("Presunto");
		ingredientes.add("Queijo");
		return ingredientes;
	}

	@Override
	protected int obterTempoDeForno() {
		return 30;
	}
	
	

}
