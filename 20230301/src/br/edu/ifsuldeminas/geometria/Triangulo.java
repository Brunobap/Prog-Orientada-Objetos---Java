package br.edu.ifsuldeminas.geometria;

public class Triangulo extends Geometria {

		public void metodo() {
			calcularArea();
			// Esse m�todo n�o existe mais, ele se tornou abstrato
			//super.calcularArea();
			
			// N�o pode acessar pois o m�todo � privado
			//calculoIntermediario();
			
			// Pode ser acessado pois � do mesmo pacote
			auxiliar();
		}
		
		@Override
		public float calcularArea() {
			System.out.println("A �rea do tri�ngulo � 250");
			return 100;
		}
}
