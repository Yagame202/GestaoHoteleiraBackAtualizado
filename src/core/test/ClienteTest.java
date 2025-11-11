package core.test;

import base.enumeration.Funcionalidade;
import base.exception.ClienteException;
import core.service.ClienteService;

public class ClienteTest {

    // Atributos
    private ClienteService clienteService;

    // Construtor
    public ClienteTest(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Métodos de testes

    public String testar(Funcionalidade funcionalidade) throws ClienteException {
        switch (funcionalidade) {
            case LISTAR:
                return this.listar();
            case CADASTRAR:
                return this.cadastrar();
            case ALTERAR:
                return this.alterar();
            case EXCLUIR:
                return this.excluir();
            default:
                return null;
        }
    }

    public String listar() throws ClienteException {
        return clienteService.listar();
    }

    public String cadastrar() throws ClienteException {
        // Dados para cadastro
        String nomeCompleto = "João Carlos Silva";
        String dataNascimento = "10/01/2000";
        String documento = "362.568.545-58";
        String pais = "Brasil";
        String estado = "Santa Catarina";
        String cidade = "Blumenau";
        String fidelidade = "Sim";
        String observacao = "Bem humorado";

        return clienteService.cadastrar(nomeCompleto, dataNascimento, documento, pais, estado, cidade, fidelidade, observacao);
    }

    public String alterar() throws ClienteException {
        // Dados para alteração
        String id = "3";
        String nomeCompleto = "Júlia Machado";
        String dataNascimento = "29/08/1997";
        String documento = "236.963.958-69";
        String pais = "Brasil";
        String estado = "Rio de Janeiro";
        String cidade = "Rio de Janeiro";
        String fidelidade = "Não";
        String observacao = "Primeira vez no hotel.";

        return clienteService.alterar(id, nomeCompleto, dataNascimento, documento, pais, estado, cidade, fidelidade, observacao);
    }

    public String excluir() throws ClienteException {
        return clienteService.excluir("3");
    }

}
