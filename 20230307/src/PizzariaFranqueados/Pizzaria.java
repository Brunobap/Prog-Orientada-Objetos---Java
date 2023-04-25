package PizzariaFranqueados;
import java.util.List;

public abstract class Pizzaria {
	public abstract List<String> obterIngredientes();
	
	public void misturarIngredientes(List<String> ingredientes) {
		System.out.println("Misturando ingredientes\n");
		for(String ingrediente : ingredientes) {
			System.out.printf("%s\n",ingrediente);
		}
	}
	
	protected abstract int obterTempoDeForno();
	
	protected void assar(int tempo) {
		System.out.printf("Assando a pizza por %d minutos ",tempo);
	}
}
