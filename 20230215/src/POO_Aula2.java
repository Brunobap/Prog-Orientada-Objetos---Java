
// Aqui foi usada as classes Aluno (Aluno.java) e Professor (Professor.java)

public class POO_Aula2 {
	public static void main(String[] args) {
		
		/* Parte 1: Aprender a IDE 
		System.out.println("Olá Mundo!");
		 
		System.out.println("Você tem " + args.length + " argumentos");
		if (args.length > 0) System.out.println("Seja bem vindo, " + args[0]);
		*/
		
		/* Parte 2: Usar argumentos 
		if (args.length > 0) {
			int num1 = Integer.parseInt(args[0]);
		 	int num2 = Integer.parseInt(args[1]);
		 	String str = String.format("A soma de %s e %s é %d", args[0], args[1], num1 + num2);
		 	System.out.println(str);
		}*/		

		/* Parte 3: Teste dos objetos
		 Aluno a = new Aluno(67432, "Jucelino");
		 System.out.println(a);
		 Aluno b = new Aluno(2396, "Jubileo");
		 System.out.println(b);
		 Aluno c = b;
		 System.out.println(c);
		 
		 if (a == b) System.out.println("a == b");
		 else System.out.println("a =/= b");
		  
		 if (a == c) System.out.println("a == c");
		 else System.out.println("a =/= c");
		 
		 if (b == c) System.out.println("b == c");
		 else System.out.println("b =/= c");
		 
		 a.nome = "Jubileo"; a.RA = 2396;
		 if (a == b) System.out.println("Agora, a == b");
		 else System.out.println("\nAinda a =/= b");*/
		
		/* Parte 4: Alteração de objetos 
		Aluno a1 = new Aluno(26387,"Jubileo");
		System.out.println("Nome 1: " + a1.nome);
		
		AlteraAluno(a1);
		System.out.println("Nome 2: " + a1.nome);
		
		a1 = AlteraAluno(a1);
		if (a1 == null) System.out.println("Nome 3: ");
		else System.out.println("Nome 3: " + a1.nome);				
	}
	
	public static Aluno AlteraAluno(Aluno a) {
		a.nome = "Jurimeia";
		a = null;
		return a;*/
		
		/* Parte 5: Objetos em objetos */
		Aluno a2 = new Aluno();
		System.out.println("Aluno: " + a2);
		System.out.println("RA do aluno: " + a2.RA);
		System.out.println("nome do aluno: " + a2.nome);
		System.out.println("professor do aluno: " + a2.p);
		System.out.println("id do professor do aluno: " + a2.p.id);
		System.out.println("nome do professor do aluno: " + a2.p.nome);
	}
}
