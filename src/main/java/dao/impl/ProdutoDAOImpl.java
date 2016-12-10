/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.impl;

import dao.ProdutoDAO;
import java.util.ArrayList;
import model.Produto;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vinicius
 */
@Repository
public class ProdutoDAOImpl implements ProdutoDAO {

    /**
     * Objeto de sessao com Banco de Dados
     */
    @Autowired
    //Caso houvesse mais de um bean tipo SessionFactory, seria preciso:
    //@Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Metodo chamado para atualizar produto na tabela Produto do banco de dados.
     * @param Produto
     */
    @Transactional  //Delega ao Hibernate o controle de transacoes
    public void atualizar (Produto produto){
//==========================Como seria sem o txManager:=========================
//        Session session = this.sessionFactory.getCurrentSession();
//        Transaction trans = null;
//        if (cliente != null)
//            try {
//                trans = session.beginTransaction();
//                session.update(produto);
//                trans.commit();
//            } catch (Exception e) {
//                trans.rollback();
//            } finally {
//                session.close();
//            }
//==========================Como fica com o txManager:==========================
        this.sessionFactory.getCurrentSession().update(produto);
    }

    /**
     * Metodo chamado para excluir produto na tabela Produto do banco de dados.
     * @param Produto
     */
    @Transactional
    public void excluir (Produto produto) {
        this.sessionFactory.getCurrentSession().delete(produto);
    }

    /**
     * Metodo chamado para adicionar produto na tabela Produto do banco de dados.
     * @param Produto
     */
    @Transactional
    public void salvar (Produto produto) {
        this.sessionFactory.getCurrentSession().save(produto);
    }

    /**
     * Metodo chamado para listar todos os produtos na tabela Produto do banco de dados.
     * @return List<Produtos>
     */
    @Transactional(readOnly=true) //Somente operacao de leitura
    public List todosProdutos () { 
        try{
            List listCliente = this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Produto").list();
            return listCliente;
        } catch (Exception e){
            return new ArrayList<Produto>();
        }
    }

    /**
     * Metodo chamado para executar query para procurar produto na tabela
     * Produto do banco de dados. Parametro da consulta: codigo.
     * @param codigo
     * @return Produto
     */
    @Transactional(readOnly=true) //Somente operacao de leitura
    public Produto procurarProduto(int codigo) {
        try{
            return (Produto) this.sessionFactory.getCurrentSession()
                    .get(Produto.class, codigo);
        } catch (Exception e){
            return new Produto();
        }
    }

}
