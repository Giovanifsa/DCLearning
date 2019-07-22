package ecommerce.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String nome;
	private String email;
	private byte[] senhaHasheada;
	
	public Usuario() {}

	public Usuario(String nome, String email, byte[] senhaHasheada) {
		this.nome = nome;
		this.email = email;
		this.senhaHasheada = senhaHasheada;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getSenhaHasheada() {
		return senhaHasheada;
	}

	public void setSenhaHasheada(byte[] senhaHasheada) {
		this.senhaHasheada = senhaHasheada;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
