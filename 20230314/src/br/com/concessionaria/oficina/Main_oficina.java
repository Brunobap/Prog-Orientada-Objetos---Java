package br.com.concessionaria.oficina;
import br.com.concessionaria.funcionarios.*;
import br.com.concessionaria.estrutura.*;
import br.com.concessionaria.veiculos.*;

public class Main_oficina {

	public static void main(String[] args) {
		Oficina ofi1 = new Oficina("Martelinho de Ouro");
		
		ofi1.adicionarMecanico(new Mecanico("Zé", 20, 3));
		ofi1.adicionarMecanico(new Mecanico("Jão", 5, 1));

		ofi1.adicionarPeca(new Peca("Carburador", 2000, "20201030"));
		ofi1.adicionarPeca(new Peca("Cilindro", 520, "20210222"));
		ofi1.adicionarPeca(new Peca("Tapete", 200, "20230129"));
		
		Veiculo kwid20 = new Veiculo("21e1ec", 2000, 4, "y923e", "Amarelo", 200);
		Veiculo uno2000 = new Veiculo("29mc0u", 2020, 27, "2hn3rf", "Verde", 1400);
		Veiculo corsa72 = new Veiculo("04n5y9", 1970, 49, "10m2dv", "Preto", 5200);

		ofi1.adicionarVeiculo(kwid20);
		ofi1.adicionarVeiculo(uno2000);
		ofi1.adicionarVeiculo(corsa72);
		
		System.out.println("Nome da oficina: "+ ofi1.getNomeOficina());
		System.out.println("Lista de Mecanicos: "+ ofi1.getListaMecanicos());
		System.out.println("Lista de Pecas: "+ ofi1.getListaPecas());
		System.out.println("Lista de veículos: "+ ofi1.getListaVeiculos());
		System.out.println("Peças necessárias: "+ ofi1.getPecasNecessarias());
	}
}
