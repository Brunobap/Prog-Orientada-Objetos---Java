package PizzariaFranqueados;
import java.util.*;

public abstract class Pizzaria {
	
	public Pizzaria() {
		ingredientes = new ArrayList<String>();
		setIngredientesBase();
	}
	
	private List<String> ingredientes;
	public List<String> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<String> ingredientes) {
		this.ingredientes = ingredientes;
		
	}
	private void setIngredientesBase() {
		ingredientes.add("Farinha");
		ingredientes.add("Ovo");
		ingredientes.add("Leite");
	}

	protected abstract List<String> obterIngredientes();
	protected abstract int obterTempoDeForno();
	
	public void realizarProcesso() {
		setIngredientes(obterIngredientes());
		misturarIngredientes(getIngredientes());
		int tempo = obterTempoDeForno();
		assar(tempo);
	}
	
	protected void misturarIngredientes(List<String> ingredientes) {
		System.out.println("Misturando ingredientes\n");
		for(String ingrediente : ingredientes) {
			System.out.printf("%s\n",ingrediente);
		}
	}
	
	protected void assar(int tempo) {
		System.out.printf("Assando a pizza por %d minutos ",tempo);
	}
}
