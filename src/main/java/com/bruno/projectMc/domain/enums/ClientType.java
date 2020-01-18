package com.bruno.projectMc.domain.enums;

public enum ClientType {

	PESSOAFISICA(0, "Pessoa Física"), PESSOAJURIDICA(1, "Pessoa Jurídica");

	private int cod;
	private String description;

	private ClientType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static ClientType toEnum(Integer cod) {
			
		if (cod == null) {
			return null;
		}
		for (ClientType x : ClientType.values()) {
			if (cod.equals((x.getCod()))) {
					return x;
		}
	}
	throw new IllegalArgumentException("id invalido" + cod);
	
}
}