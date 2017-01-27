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
@Table(name = "produto")
@AttributeOverride(name = "objectID", column = @Column(name = "produto_id") )
@SequenceGenerator(name = "SEQUENCE", sequenceName = "produto_produto_id_seq")
public class Produto extends BaseModel {

    @Column(name = "nome") // (name = "nome") somente necessario quando atributo
                           // e coluna forem diferentes
    private String nome;

    @Column
    private String descricao;

    @Column
    private int codigo;

    @Column
    private float preco;

    @Column
    private int qtd;

    public Produto(String nome, String descricao, int codigo, float preco, int qtd) {
        this.nome = nome;
        this.descricao = descricao;
        this.codigo = codigo;
        this.preco = preco;
        this.qtd = qtd;
    }

    public Produto() {
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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao
     *            the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the preco
     */
    public float getPreco() {
        return preco;
    }

    /**
     * @param preco
     *            the preco to set
     */
    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQtd() {
        if (qtd == 0) {
            qtd = 1;
        }
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

}
