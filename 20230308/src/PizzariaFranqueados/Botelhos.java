package PizzariaFranqueados;

import java.util.ArrayList;
import java.util.List;

public class Botelhos extends Pizzaria {
	
	@Override
	public List<String> obterIngredientes() {
		List<String> ingredientes = new ArrayList<String>();
		ingredientes.add("Molho");
		return ingredientes;
	}

	@Override
	protected int obterTempoDeForno() {
		return 15;
	}

}
