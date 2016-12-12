/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import model.Endereco;

/**
 *
 * @author vinicius
 */
public interface EnderecoDAO {

	/**
	 * Metodo para atualizar objeto endereco na tabela endereco.
	 * 
	 * @param endereco
	 * @throws EmpresaDAOException
	 */
	void atualizar(Endereco endereco);

	/**
	 * Metodo para excluir endereco da tabela endereco.
	 * 
	 * @param endereco
	 * @throws EmpresaDAOException
	 */
	void excluir(Endereco endereco);

	/**
	 * Metodo para adicionar objeto endereco na tabela endereco.
	 * 
	 * @param endereco
	 * @throws EmpresaDAOException
	 */
	void salvar(Endereco endereco);

	Endereco procurarEndereco(int cep);

}
