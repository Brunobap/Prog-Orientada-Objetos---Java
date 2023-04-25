
public class Aluno {
	public int RA;
	public String nome;
	public Professor p;
	
	public Aluno() {
		this.RA = 0;
		this.nome = "Harry Potter";
		this.p = new Professor();
	}
	
	public Aluno(int RA, String nome) {
		this.RA = RA;
		this.nome = nome;
		this.p = new Professor();
	}
}
