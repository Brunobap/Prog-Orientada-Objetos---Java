package br.edu.ifsuldeminas.geometria;

public class Triangulo extends Geometria {

		public void metodo() {
			calcularArea();
			// Esse método não existe mais, ele se tornou abstrato
			//super.calcularArea();
			
			// Não pode acessar pois o método é privado
			//calculoIntermediario();
			
			// Pode ser acessado pois é do mesmo pacote
			auxiliar();
		}
		
		@Override
		public float calcularArea() {
			System.out.println("A área do triângulo é 250");
			return 100;
		}
}
