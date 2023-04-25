
public class Pessoa {
	private String cpf;
	private String nome;
	
	// Nessa regra, as pessoas são iguais se tiverem o mesmo cpf
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pessoa)) {
			return false;
		}
		
		Pessoa outra = (Pessoa) obj;
		return cpf.equals(outra.cpf);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
