

public class Operacoes {

	private int numero1;
	private int numero2;

	public int getNumero1() { 
		return numero1;
	}
	public void setNumero1(int numero1) {
		this.numero1 = numero1;
	}

	public int getNumero2() {
		return numero2;
	}
	public void setNumero2(int numero2) {
		this.numero2 = numero2;
	}

	public int Somar() {
		return this.getNumero1() + this.getNumero2();
	}	
	public int Subtrair() {
		return this.getNumero1() - this.getNumero2();
	}	
	public int Multiplicar() {
		return this.getNumero1() * this.getNumero2();
	}	
	public int Dividir() {
		return this.getNumero1() / this.getNumero2();
	}
		
}
