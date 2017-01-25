/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import model.Cliente;
import model.Endereco;
import model.EnumPerfil;

/**
 *
 * @author Vinícius. Alter: grupo Bertran, Cláudio, Manoel e Thiago
 */
@Component
public class ClienteBean {

    private Cliente cliente = new Cliente();
    private boolean logado = false;
    private boolean sexoB;
    private boolean perfilB;
    private DataModel model;
    private Endereco endereco;
    private Cliente usuarioLogado;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private EnderecoDAO enderecoDAO;

    private static final List<SelectItem> UFs;
    private List<EnumPerfil> perfis;
    private EnumPerfil perfil;

    private boolean erroEndereco;

    private Endereco enderecoSelecionado;

    static {
        UFs = new ArrayList<SelectItem>(27);
        UFs.add(new SelectItem("Acre", "AC"));
        UFs.add(new SelectItem("Alagoas", "AL"));
        UFs.add(new SelectItem("Amapá", "AP"));
        UFs.add(new SelectItem("Amazonas", "AM"));
        UFs.add(new SelectItem("Bahia", "BA"));
        UFs.add(new SelectItem("Ceará", "CE"));
        UFs.add(new SelectItem("Distrito Federal", "DF"));
        UFs.add(new SelectItem("Espírito Santo", "ES"));
        UFs.add(new SelectItem("Goiás", "GO"));
        UFs.add(new SelectItem("Maranhão", "MA"));
        UFs.add(new SelectItem("Mato Grosso", "MT"));
        UFs.add(new SelectItem("Mato Grosso do Sul", "MS"));
        UFs.add(new SelectItem("Minas Gerais", "MG"));
        UFs.add(new SelectItem("Pará", "PA"));
        UFs.add(new SelectItem("Paraíba", "PB"));
        UFs.add(new SelectItem("Paraná", "PR"));
        UFs.add(new SelectItem("Pernambuco", "PE"));
        UFs.add(new SelectItem("Piauí", "PI"));
        UFs.add(new SelectItem("Rio de Janeiro", "RJ"));
        UFs.add(new SelectItem("Rio Grande do Norte", "RN"));
        UFs.add(new SelectItem("Rio Grande do Sul", "RS"));
        UFs.add(new SelectItem("Rondônia", "RO"));
        UFs.add(new SelectItem("Roraima", "RR"));
        UFs.add(new SelectItem("Santa Catarina", "SC"));
        UFs.add(new SelectItem("São Paulo", "SP"));
        UFs.add(new SelectItem("Sergipe", "SE"));
        UFs.add(new SelectItem("Tocantins", "TO"));

    }

    /**
     * @inicia a lista com os perfis assim que o bean for iniciado. inicia
     *         outros métodos.
     */
    @PostConstruct
    public void init() {
        this.perfis = Arrays.asList(EnumPerfil.values());
    }

    public boolean getPerfilB() {
        if (this.cliente.getPerfil().equals("Administrador")) {
            setPerfilB(true);
        } else if (this.cliente.getPerfil().equals("Comum")) {
            setPerfilB(false);
        }
        return perfilB;
    }

    public void setPerfilB(boolean perfilB) {
        this.perfilB = perfilB;
        if (perfilB == true) {
            this.cliente.setPerfil(EnumPerfil.Administrador);
        } else if (perfilB == false) {
            this.cliente.setPerfil(EnumPerfil.Comum);
        }
    }

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
     *            the cliente to set. Form
     */

    public void setCliente(Cliente cliente) {
        cliente.setNome(cliente.getNome().trim());
        cliente.setSenha(cliente.getSenha().trim());
        cliente.setTelefoneCelular(cliente.getTelefoneCelular().trim());
        cliente.setTelefoneFixo(cliente.getTelefoneFixo().trim());
        cliente.setUsuario(cliente.getUsuario().trim());
        // cliente.setPerfil(cliente.getPerfil());
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
        if (endereco == null) {
            endereco = new Endereco();
        }
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
            this.setUsuarioLogado(cli);
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
        Cliente c = new Cliente("", "", "", "", "", "", null, 0, null, null);
        setCliente(c);
        this.setUsuarioLogado(null);
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
        try {
            setCliente(cliente); // Limpa espacos em branco das strings
            return "atualizarCliente";
        } catch (NullPointerException n) {
            return "mostrarCliente";
        }
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

    public void atualizarEnderecoCliente() {
        clienteDAO.atualizar(cliente);
        // setCliente(clienteDAO.procurarCliente(cliente.getCpf()));
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
        this.setUsuarioLogado(null);
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
        Cliente c = new Cliente("", "", "", "", "", "", null, 0, new ArrayList<Endereco>(), EnumPerfil.Comum);
        setCliente(c);
        setLogado(false);
        return "cadastrarCliente";

    }

    public void verificaListaEnderecosCliente() {
        if (this.cliente.getEnderecos().isEmpty()) {
            setErroEndereco(true);
        } else {
            setErroEndereco(false);
        }
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
        if (isErroEndereco()) {
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    FacesHelper.getMessage("cliente.enderecoVazio"), "");
            contexto.addMessage(null, msg);
            return null;
        } else {
            setErroEndereco(false);
            cliente.setPerfil(EnumPerfil.Comum);
            clienteDAO.salvar(cliente);
            setLogado(true);
            return "mostrarCliente?faces-redirect=true";
        }
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
    public void excluirEndereco() {
        getEnderecoManipulacao();
        // enderecoDAO.excluir(this.endereco);
        this.cliente.getEnderecos().remove(this.endereco);
        this.endereco = null;
        atualizarEnderecoCliente();
    }

    public void inicializaEnderecoEdicao() {
        getEnderecoManipulacao();
    }

    public void inicializaEndereco() {
        this.endereco = new Endereco();
    }

    public void adicionarEnderecoClienteNovo() {
        this.cliente.getEnderecos().add(getEndereco());
        this.endereco = new Endereco();
    }

    public void Endereco() {
        if (!this.cliente.getEnderecos().isEmpty()) {
            this.cliente.getEnderecos().clear();
        }
    }

    public void editarEnderecoClienteNovo() {
        setEndereco(enderecoSelecionado);
        this.cliente.getEnderecos()
                .remove(this.retornaIndiceEndereco(this.cliente.getEnderecos(), enderecoSelecionado));
    }

    public int retornaIndiceEndereco(List<Endereco> enderecos, Endereco enderecoSelecionado) {
        int retorno = 0;
        for (Endereco endereco : enderecos) {
            if (endereco.getCep() == enderecoSelecionado.getCep()
                    && endereco.getCidade().equals(enderecoSelecionado.getCidade())
                    && (endereco.getEstado().equals(enderecoSelecionado.getEstado())
                            && (endereco.getRua().equals(enderecoSelecionado.getRua())
                                    && (endereco.getSetor().equals(enderecoSelecionado.getSetor()))))) {
                break;
            } else {
                retorno++;
            }
        }
        return retorno;
    }

    public void adicionarEnderecoClienteExistente() {
        int indice = this.cliente.getEnderecos().indexOf(this.endereco);
        if (!this.cliente.getEnderecos().isEmpty()) {
            if (indice >= 0) {
                this.cliente.getEnderecos().set(indice, this.endereco);
                atualizarEnderecoCliente();
                this.endereco = new Endereco();
            } else {
                // this.endereco.setClienteId(cliente.getObjectID());
                this.cliente.getEnderecos().add(this.endereco);
                atualizarEnderecoCliente();
                this.endereco = new Endereco();
            }
        } else {
            // this.endereco.setClienteId(cliente.getObjectID());
            this.cliente.getEnderecos().add(this.endereco);
            atualizarEnderecoCliente();
            this.endereco = new Endereco();
        }
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
        // this.endereco.setClienteId(cliente.getObjectID());
        this.cliente.getEnderecos().add(this.endereco);
        atualizarCliente();
        return "mostrarCliente";
    }

    public List<SelectItem> getListaUFs() {
        return UFs;
    }

    public List<EnumPerfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<EnumPerfil> perfis) {
        this.perfis = perfis;
    }

    public EnumPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumPerfil perfil) {
        this.perfil = perfil;
    }

    // <<<<<<< HEAD

    public Cliente getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Cliente usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public boolean isErroEndereco() {
        return erroEndereco;
    }

    public void setErroEndereco(boolean erroEndereco) {
        this.erroEndereco = erroEndereco;
    }

    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }

    public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
        this.enderecoSelecionado = enderecoSelecionado;
    }
    // >>>>>>> branch 'master' of https://github.com/manoel-an/SpringJSF.git

}