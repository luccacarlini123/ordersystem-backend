package com.mouzetech.ordersystem.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer cod;
	private String descricao;
	
	private Perfil(Integer cod, String  descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static Perfil toEnum(Integer id) {
		if(id == null) {
			return null;
		}
		for(Perfil x : Perfil.values()) {
			if(id.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod inválido: "+id);
	}
	
}
