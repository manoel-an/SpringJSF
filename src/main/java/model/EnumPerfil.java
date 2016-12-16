package model;

public enum EnumPerfil {
	
	Administrador("Administrador"), Comum("Comum");

	private String descricao;

	private EnumPerfil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	


	
}
