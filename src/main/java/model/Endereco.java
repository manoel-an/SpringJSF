/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author vinicius
 */
@Entity
@Table(name = "endereco")
@AttributeOverride(name = "objectID", column = @Column(name = "endereco_id"))
@SequenceGenerator(name = "SEQUENCE", sequenceName = "endereco_endereco_id_seq")
public class Endereco extends BaseModel {

	@Column
	private String rua;

	@Column
	private String setor;

	@Column
	private String complemento;

	@Column
	private String cidade;

	@Column
	private String estado;

	@Column
	private int cep;

	@Column(name = "cliente_id") // coluna e atributo tem nomes diferntes, por
	private long clienteId;

	/**
	 * @return the rua
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * @param rua
	 *            the rua to set
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * @return the setor
	 */
	public String getSetor() {
		return setor;
	}

	/**
	 * @param setor
	 *            the setor to set
	 */
	public void setSetor(String setor) {
		this.setor = setor;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento
	 *            the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the cep
	 */
	public int getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(int cep) {
		this.cep = cep;
	}

	/**
	 * @return the clienteId
	 */
	public long getClienteId() {
		return clienteId;
	}

	/**
	 * @param clienteId
	 *            the clienteId to set
	 */
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}

}
