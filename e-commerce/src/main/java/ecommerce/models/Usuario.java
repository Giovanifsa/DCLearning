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
	private String email;
	private byte[] senhaHasheada;
	private boolean admin;
	
	public Usuario() {}

	public Usuario(String email, byte[] senhaHasheada) {
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
