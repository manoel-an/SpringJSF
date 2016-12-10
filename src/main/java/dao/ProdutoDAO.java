/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import model.Produto;

/**
 *
 * @author vinicius
 */
public interface ProdutoDAO {

    /**
     * Metodo usado para atualizar produto na tabela Produto
     * @param produto
     * @throws EmpresaDAOException
     */
    void atualizar (Produto produto);

    /**
     * Metodo chamado para excluir produto da tabela Produto
     * @param produto
     * @throws EmpresaDAOException
     */
    void excluir (Produto produto);

    /**
     * Metodo chamado para adicionar produto na tabela Produto
     * @param produto
     * @throws EmpresaDAOException
     */
    void salvar (Produto produto);

    /**
     * Metodo chamado para listar todos os produtos da tabela Produto
     * @return List<Produto>
     * @throws EmpresaDAOException
     */
    List todosProdutos ();

    /**
     * Metodo chamado para retornar um especifico produto, consultado pelo codigo
     * @param codigo
     * @return produto
     * @throws EmpresaDAOException
     */
    Produto procurarProduto(int codigo);

}
