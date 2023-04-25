package br.com.concessionaria.oficina;
import java.util.*;
import br.com.concessionaria.funcionarios.*;
import br.com.concessionaria.veiculos.*;
import br.com.concessionaria.estrutura.*;

public class Oficina {
	
	private String nomeOficina;
	private List<Mecanico> listaMecanicos;
	private List<Peca> listaPecas;
	private List<Veiculo> listaVeiculos;
	private int pecasNecessarias;
	
	public Oficina (String nomeOficina) {
		this.nomeOficina = nomeOficina;
		this.listaMecanicos = new ArrayList<Mecanico>();
		this.listaPecas = new ArrayList<Peca>();
		this.listaVeiculos = new ArrayList<Veiculo>();
		this.pecasNecessarias = 0;
	}
	
	public String getNomeOficina() {
		return nomeOficina;
	}
	public void setNomeOficina(String nomeOficina) {
		this.nomeOficina = nomeOficina;
	}
	public List<Mecanico> getListaMecanicos() {
		return listaMecanicos;
	}
	public void setListaMecanicos(List<Mecanico> listaMecanicos) {
		this.listaMecanicos = listaMecanicos;
	}
	public List<Peca> getListaPecas() {
		return listaPecas;
	}
	public void setListaPecas(List<Peca> listaPecas) {
		this.listaPecas = listaPecas;
	}
	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}
	public void setListaVeiculos(List<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
	public int getPecasNecessarias() {
		return pecasNecessarias;
	}
	public void setPecasNecessarias(int pecasNecessarias) {
		this.pecasNecessarias = pecasNecessarias;
	}
	
	public void adicionarMecanico (Mecanico mecanico) {
		this.listaMecanicos.add(mecanico);
	}
	public void removeMecanico (Mecanico mecanico) {
		this.listaMecanicos.remove(mecanico);
	}

	public void adicionarPeca (Peca peca) {
		this.listaPecas.add(peca);
	}
	public void removePeca (Peca peca) {
		this.listaPecas.remove(peca);
	}

	public void adicionarVeiculo (Veiculo veiculo) {
		this.listaVeiculos.add(veiculo);
		Random rand = new Random();
		int n = rand.nextInt(3);
		for (int i=0; i<n; i++) {
			Peca nova = new Peca("Peca "+i, (i+1)*1000, "20230315");
			this.adicionarPeca(nova);	
		}
	}
	public void removeVeiculo (Veiculo veiculo) {
		this.listaVeiculos.remove(veiculo);
		Random rand = new Random();
		int n = rand.nextInt(3);
		List<Peca> peca = this.getListaPecas();
		for (int i=0; i<n; i++) {
			peca.remove(rand.nextInt(peca.size()));	
		}
	}
}
