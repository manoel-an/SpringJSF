/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.impl;

import dao.EnderecoDAO;
import model.Endereco;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vinicius
 */
@Repository
public class EnderecoDAOImpl implements EnderecoDAO {


    /**
     * Objeto de sessao com Banco de Dados
     */
    @Autowired
    //Caso houvesse mais de um bean tipo SessionFactory, seria preciso:
    //@Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Metodo chamado para atualizar endereco na tabela Endereco do banco de dados
     * @param Endereco
     */
    @Transactional //Delega ao Hibernate o controle de transacoes
    public void atualizar (Endereco endereco){
//==========A atualizacao do endereco poderia ser feita individualmente=========
//==========================Como seria sem o txManager:=========================
//        Session session = this.sessionFactory.getCurrentSession();
//        Transaction trans = null;
//        if (cliente != null)
//            try {
//                trans = session.beginTransaction();
//                session.update(endereco);
//                trans.commit();
//            } catch (Exception e) {
//                trans.rollback();
//            } finally {
//                session.close();
//            }
//=========================Como fica com o txManager:===========================
         this.sessionFactory.getCurrentSession().update(endereco);
    }

    /**
     * Metodo chamado para inserir endereco na tabela Endereco do banco de dados
     * @param Endereco
     */
    @Transactional
    public void salvar (Endereco endereco){
        this.sessionFactory.getCurrentSession().save(endereco);
    }

    /**
     * Metodo chamado para excluir endereco na tabela Endereco do banco de dados
     * @param Endereco
     */
    @Transactional
    public void excluir (Endereco endereco){
        this.sessionFactory.getCurrentSession().delete(endereco);
    }

}
