/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ProdutoDAO;
import model.Produto;

/**
 *
 * @author vinicius
 */
@Component
public class ProdutoBean {

    private Produto produto;
    @Autowired
    private ProdutoDAO produtoDAO;

    private float total;

    private float subtotal = 0f;

    /**
     * @return the produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * @param produto
     *            the produto to set
     */
    public void setProduto(Produto produto) {
        produto.setDescricao(produto.getDescricao().trim());
        produto.setNome(produto.getNome().trim());
        this.produto = produto;
    }

    // METODOS PARA MANIPULACAO DE DADOS DO PRODUTO
    /**
     * Metodo chamado para criar novo objeto Produto.
     * 
     * @return - String - retorna pagina/formulario para insercao de novo
     *         produto.
     */
    public String novoProduto() {
        Produto p = new Produto("", "", 0, 0, 0);
        setProduto(p);
        return "cadastrarProduto";
    }

    /**
     * Metodo chamado para retornar uma estrutura com todos os objetos Produto
     * no BD.
     * 
     * @return - DataModel - model.
     * @throws EmpresaDAOException
     */
    // public DataModel getTodosProdutos() {
    // model = new ListDataModel(produtoDAO.todosProdutos());
    // return model;
    // }
    public List getTodosProdutos1() {
        List<Produto> lstProd = new ArrayList<Produto>(0);
        if (lstProd.size() == 0) {
            lstProd = produtoDAO.todosProdutos();
        }
        return lstProd;
    }

    /**
     * Metodo chamado para buscar objeto Produto do DataModel; em segida,
     * atribui o produto do DataModel ao atributo produto da classe ProdutoBean.
     * Quando usuario clica no link da tabela para atualizar ou excluir produto.
     */
    // public void getProdutoManipulacao() {
    // setProduto((Produto) model.getRowData());
    // }

    /**
     * Metodo chamado para salvar as alteracoes no produto no Banco de Dados
     * (BD).
     * 
     * @return - String - retorna pagina com todos os produtos disponiveis no
     *         BD.
     * @throws EmpresaDAOException
     */
    public String atualizarProduto() {
        produtoDAO.atualizar(produto);
        return "mostrarProdutos";
    }

    public String retornarPaginaProdutos() {
        return "mostrarProdutos";
    }

    public void init() {
        @SuppressWarnings("unchecked")
        List<Produto> itens = getTodosProdutos1();
        float subtotal = 0f;
        if (itens.size() > 0) {
            for (Produto produto : itens) {
                subtotal += (produto.getPreco() * produto.getQtd());
            }
        }
        setTotal(subtotal);
    }

    public void incrementarProduto() {
        this.subtotal = 0f;
        int qtd = this.produto.getQtd();
        qtd++;
        if (qtd > 99) {
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    FacesHelper.getMessage("produtos.qtdLimiteInvalido"), "");
            contexto.addMessage(null, msg);
            this.retornarPaginaProdutos();
        } else {
            this.produto.setQtd(qtd);
            @SuppressWarnings("unchecked")
            List<Produto> produtos = produtoDAO.todosProdutos();
            for (Produto produto : produtos) {
                this.subtotal += (produto.getPreco() * produto.getQtd());
            }
            this.subtotal += this.produto.getPreco();
            setTotal(this.subtotal);
            this.atualizarProduto();
        }
    }

    public void decrementarProduto() {
        int qtd = this.produto.getQtd();
        qtd--;
        if (qtd < 1) {
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    FacesHelper.getMessage("produtos.qtdInvalido"), "");
            contexto.addMessage(null, msg);
            this.retornarPaginaProdutos();
        } else {
            this.produto.setQtd(qtd);
            this.subtotal -= this.produto.getPreco();
            setTotal(this.subtotal);
            this.atualizarProduto();
        }
    }

    /**
     * Medodo chamado para buscar produto a ser atualizado pelo usuario.
     * 
     * @return - String - retorna a pagina/formulario para atualizar dados do
     *         produto.
     */
    public String editarProduto() {
        // getProdutoManipulacao();
        return "atualizarProduto";
    }

    /**
     * Metodo chamado para excluir um produto da tabela produto da base de dados
     * 
     * @return - String - retorna pagina com todos os produtos disponiveis no
     *         BD.
     * @throws EmpresaDAOException
     */
    public String excluirProduto() {
        // getProdutoManipulacao();
        produtoDAO.excluir(this.produto);
        return "mostrarProdutos";
    }

    /**
     * Metodo chamado para incluir/salvar novo produto no banco de dados.
     * 
     * @return - String - retorna pagina com todos os produtos disponíveis no
     *         BD.
     * @throws EmpresaDAOException
     */
    public String incluirProduto() {
        @SuppressWarnings("unchecked")
        List<Produto> produtos = produtoDAO.todosProdutos();
        for (Produto produto : produtos) {
            this.subtotal += (produto.getPreco() * produto.getQtd());
        }
        this.subtotal += (this.produto.getPreco() * this.produto.getQtd());
        setTotal(this.subtotal);
        produtoDAO.salvar(produto);
        return "mostrarProdutos";
    }

    public float getTotal() {
        this.init();
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    // Os metodos abaixo nao foram utilizados nesse projeto
    // public void validaNome(FacesContext contexto, UIComponent componente,
    // Object objeto){
    // String nom = (String)objeto;
    // if (nom == null || nom.trim().equals("") || nom.length() < 5) {
    // ((UIInput)componente).setValid(false);
    // FacesMessage msg = new
    // FacesMessage(FacesHelper.getMessage("produtos.nomeErrado"));
    // contexto.addMessage(componente.getClientId(contexto), msg);
    // }
    // }
    //
    // public void validaDescricao(FacesContext contexto, UIComponent
    // componente, Object objeto){
    // String desc = (String)objeto;
    // if (desc == null || desc.trim().equals("") || desc.length() < 6) {
    // ((UIInput)componente).setValid(false);
    // FacesMessage msg = new
    // FacesMessage(FacesHelper.getMessage("produtos.descricaoErrada"));
    // contexto.addMessage(componente.getClientId(contexto), msg);
    // }
    // }
    //
    // public void validaPreco(FacesContext contexto, UIComponent componente,
    // Object objeto){
    // float pre = (Float)objeto;
    // if (pre <= 0 || pre > 20000) {
    // ((UIInput)componente).setValid(false);
    // FacesMessage msg = new
    // FacesMessage(FacesHelper.getMessage("produtos.precoErrado"));
    // contexto.addMessage(componente.getClientId(contexto), msg);
    // }
    // }
    //
    // public void validaCodigo(FacesContext contexto, UIComponent componente,
    // Object objeto){
    // int cod = (Integer)objeto;
    // if (cod <= 0 || cod > 9999) {
    // ((UIInput)componente).setValid(false);
    // FacesMessage msg = new
    // FacesMessage(FacesHelper.getMessage("produtos.codigoErrado"));
    // contexto.addMessage(componente.getClientId(contexto), msg);
    // }
    // }
}