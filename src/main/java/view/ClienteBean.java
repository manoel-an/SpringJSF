/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import model.Cliente;
import model.Endereco;

/**
 *
 * @author vinicius
 */
@Component
public class ClienteBean {

	private Cliente cliente = new Cliente();
	private boolean logado = false;
	private boolean sexoB;
	private DataModel model;
	private Endereco endereco;

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private EnderecoDAO enderecoDAO;

	/**
	 * @return the sexoB
	 */
	public boolean getSexoB() {
		if (this.cliente.getSexo().trim().equals("masculino")) {
			setSexoB(true);
		} else if (this.cliente.getSexo().trim().equals("feminino")) {
			setSexoB(false);
		}
		return sexoB;
	}

	/**
	 * @param sB
	 *            the sexoB to set
	 */
	public void setSexoB(boolean sB) {
		this.sexoB = sB;
		if (sB == true) {
			this.cliente.setSexo("masculino");
		} else if (sB == false) {
			this.cliente.setSexo("feminino");
		}
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		cliente.setNome(cliente.getNome().trim());
		cliente.setSenha(cliente.getSenha().trim());
		cliente.setTelefoneCelular(cliente.getTelefoneCelular().trim());
		cliente.setTelefoneFixo(cliente.getTelefoneFixo().trim());
		cliente.setUsuario(cliente.getUsuario().trim());
		this.cliente = cliente;
	}

	/**
	 * @return the logado
	 */
	public boolean isLogado() {
		return logado;
	}

	/**
	 * @param logado
	 *            the logado to set
	 */
	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	// METODOS PARA MANIPULACAO DE DADOS DO CLIENTE
	/**
	 * Metodo chamado para autenticar cliente. Busca cliente por usuario e
	 * senha. Se encontra, atribui a variavel 'logado' valor true, caso
	 * contrario, retorna mensage e atribui a variavel 'logado' valor false.
	 * 
	 * @return String - Pagina do cliente com dados e enderecos (caso true);
	 *         Formulario de login com mensagem de erro (caso false).
	 * @throws EmpresaDAOException
	 */
	public String autenticaCliente() {
		String retorno;
		Cliente cli = clienteDAO.procurarCliente(this.cliente.getUsuario().trim(), this.cliente.getSenha().trim());
		if (cli != null && cli.getCpf() != 0) {
			setCliente(cli);
			setLogado(true);
			retorno = "mostrarCliente";
		} else {
			FacesContext contexto = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					FacesHelper.getMessage("cliente.nomeOUSenhaErrados"), "");
			contexto.addMessage("login", msg);
			setLogado(false);
			retorno = "login";
		}
		return retorno;
	}

	/**
	 * Metodo chamado para atribuir false ao atributo (variavel) 'logado'.
	 * 
	 * @return String - Pagina inicial do site.
	 */
	public String logout() {
		setLogado(false);
		Cliente c = new Cliente("", "", "", "", "", "", null, 0, null);
		setCliente(c);
		return "empresa";
	}

	/**
	 * Metodo chamado quando usuario deseja atualizar dados (do cliente - o
	 * usuario e o cliente).
	 * 
	 * @return String - retorna pagina/formulario para atualizacao de dados do
	 *         cliente.
	 */
	public String editarCliente() {
		setCliente(cliente); // Limpa espacos em branco das strings
		return "atualizarCliente";
	}

	/**
	 * Metodo chamado para salvar as alteracoes feitas nos dados do cliente -
	 * atualizacao. Atualizacao inclui as de endereco
	 * 
	 * @return - String - retorna pagina do cliente com respectivos dados e
	 *         enderecos.
	 */
	public String atualizarCliente() {
		clienteDAO.atualizar(cliente);
		// setCliente(clienteDAO.procurarCliente(cliente.getCpf()));
		return "mostrarCliente";
	}

	/**
	 * Metodo chamado para excluir registro do cliente. Ao excluir o cliente,
	 * exclui-se seus enderecos. Ao se excluir o cliente, atribui-se a variavel
	 * (propriedade) 'logado' o valor false.
	 * 
	 * @return - String - pagina inicial do site.
	 * @throws EmpresaDAOException
	 */
	public String excluirCliente() {
		clienteDAO.excluir(cliente);
		setLogado(false);
		this.cliente = new Cliente();
		return "empresa";
	}

	/**
	 * Metodo chamado para instanciar um novo objeto tipo Cliente.
	 * 
	 * @return - String - retorna pagina/formulario para inserir dados do
	 *         cliente.
	 */
	public String novoCliente() {
		Cliente c = new Cliente("", "", "", "", "", "", null, 0, new ArrayList<Endereco>());
		setCliente(c);
		setLogado(false);
		return "cadastrarCliente";
	}

	/**
	 * Metodo chamado para salvar os dados do novo cliente, informados no
	 * formulario.
	 * 
	 * @return - String - retorna pagina do cliente com respectivos dados (neste
	 *         momento nao ha endereco).
	 * @throws EmpresaDAOException
	 */
	public String salvarCliente() {
		clienteDAO.salvar(cliente);
		setLogado(true);
		return "mostrarCliente";
	}

	// METODOS PARA MANIPULACAO DE DADOS DO ENDERECO
	/**
	 * Metodo chamado para exebir todos os enderecos de um determinado cliente.
	 * 
	 * @return DataModel - model - uma estrutura de dados para armazenar
	 *         enderecos.
	 */
	public DataModel getEnderecos() {
		model = new ListDataModel(this.cliente.getEnderecos());
		return model;
	}

	/**
	 * Metodo chamado para identificar endereco escolhido para atualizacao, via
	 * getEnderecoManipulacao().
	 * 
	 * @return - Retorna pagina/formulario para que usuario possa atualizar os
	 *         dados do endereco.
	 */
	public String editarEndereco() {
		getEnderecoManipulacao();
		return "editarEndereco";

	}

	/**
	 * Retira endereco a ser excluido da lista de endereco do objeto usuario; em
	 * seguida, exclui o endereco diretamente da tabela endereco do banco de
	 * dados.
	 * 
	 * @return String - mostra pagina do cliente com respectivos dados e
	 *         enderecos
	 * @throws EmpresaDAOException
	 */
	public String excluirEndereco() {
		getEnderecoManipulacao();
		// enderecoDAO.excluir(this.endereco);
		this.cliente.getEnderecos().remove(this.endereco);
		this.endereco = null;
		atualizarCliente();
		return "mostrarCliente";
	}

	/**
	 * Metodo chamado para buscar objeto Endereco do DataModel. Quando usuario
	 * clica no link da tabela para atualizar ou excluir endereco. O index é
	 * importanta porque na atualização, sobrescreve-se objeto endereco na lista
	 * de enderecos, gracas a posicao index: lista.set(index, objeto).
	 */
	public void getEnderecoManipulacao() {
		setEndereco((Endereco) model.getRowData());
	}

	/**
	 * Metodo chamado quando usuario solicita novo endereco
	 * 
	 * @return String - mostra pagina/formulario para usuario inserir dados do
	 *         novo endereco
	 */
	public String novoEndereco() {
		this.endereco = new Endereco();
		return "novoEndereco";
	}

	/**
	 * Metodo chamado quando usuario deseja salvar novo endereco
	 * 
	 * @return String - mostra pagina do cliente com respectivos dados e
	 *         enderecos
	 */
	public String salvarEndereco() {
		this.endereco.setClienteId(cliente.getObjectID());
		this.cliente.getEnderecos().add(this.endereco);
		atualizarCliente();
		return "mostrarCliente";
	}

}