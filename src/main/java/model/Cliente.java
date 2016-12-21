/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author vinicius
 */
@Entity
@Table(name = "cliente")
@AttributeOverride(name = "objectID", column = @Column(name = "cliente_id"))
@SequenceGenerator(name = "SEQUENCE", sequenceName = "cliente_cliente_id_seq")
public class Cliente extends BaseModel {

	@Column
	private String nome;

	@Column(name = "telefone_fixo")
	private String telefoneFixo;

	@Column(name = "telefone_celular")
	private String telefoneCelular;

	@Column
	private String sexo;

	@Column
	private String usuario;

	@Column
	private String senha;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false, length = 10)
	private Date dataNascimento;

	@Column(name = "cpf", nullable = false)
	private long cpf;

	// FetchType: EAGER anteriormente buscados; LAZY: posteriormente buscados
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinColumn(name = "cliente_id")
	private List<Endereco> enderecos;

	private EnumPerfil perfil;

	// Cpf do tipo string - para auxiliar a trabalhar com mascara.
	@Transient
	private String cpfs = "";

	public Cliente() {
	}

	public Cliente(String nome, String telefoneCelular, String telefoneFixo, String sexo, String usuario, String senha,
			Date dataNascimento, int cpf, List<Endereco> enderecos, EnumPerfil perfil) {
		this.nome = nome;
		this.telefoneCelular = telefoneCelular;
		this.telefoneFixo = telefoneFixo;
		this.sexo = sexo;
		this.usuario = usuario;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.enderecos = enderecos;
		this.perfil = perfil;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone_fixo
	 */
	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	/**
	 * @param telefoneFixo
	 *            the telefone_fixo to set
	 */
	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	/**
	 * @return the telefone_celular
	 */
	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	/**
	 * @param telefoneCelular
	 *            the telefone_celular to set
	 */
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the data_nascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento
	 *            the data_nascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the cpf
	 */
	public long getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public void setCpfs(String cpfs) {
		cpfs = cpfs.trim();
		this.cpfs = cpfs;
		try {
			cpfs = this.cpfs.substring(0, 3); // 111.111.111-11
			cpfs += this.cpfs.substring(4, 7);
			cpfs += this.cpfs.substring(8, 11);
			cpfs += this.cpfs.substring(12, 14);
		} catch (Exception e) {
			// do nothing;
		}
		this.cpf = Long.parseLong(cpfs);
	}

	public String getCpfs() {
		String cpfs = String.valueOf(this.cpf);
		try {
			cpfs = cpfs.substring(0, 3) + "." + cpfs.substring(3, 6) + "." + cpfs.substring(6, 9) + "-"
					+ cpfs.substring(9, 11);
		} catch (Exception e) {
			// do nothing;
		}
		return cpfs;
	}

	/**
	 * @return the enderecos
	 */
	public List<Endereco> getEnderecos() {
		if (enderecos == null) {
			enderecos = new ArrayList<Endereco>();
		}
		return enderecos;
	}

	/**
	 * @param enderecos
	 *            the enderecos to set
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

    @Enumerated(EnumType.STRING)
	public EnumPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(EnumPerfil perfil) {
		this.perfil = perfil;
	}

}
