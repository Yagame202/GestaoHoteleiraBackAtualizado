package core.service;

import base.enumeration.Funcionalidade;
import base.exception.ClienteException;
import base.util.Utilidades;
import core.dao.ClienteDAO;
import core.model.Cliente;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteService {

    // Atributos
    private ClienteDAO clienteDAO;

    // Construtor
    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    // Métodos públicos

    public String listar() throws ClienteException {
        ArrayList<Cliente> clientes =  clienteDAO.selecionar();
        String lista = "";
        if(clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                lista += cliente + "\n";
            }
        } else {
            lista = "Nenhum cliente encontrado.";
        }
        return lista;
    }

    public String cadastrar(
            String nomeCompleto,
            String dataNascimentoFormatoBR,
            String documento,
            String pais,
            String estado,
            String cidade,
            String fidelidade,
            String observacao
    ) throws ClienteException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, nomeCompleto, dataNascimentoFormatoBR, documento, pais, estado, cidade, fidelidade);
        if(!mensagemErro.isEmpty()) throw new ClienteException(mensagemErro);

        LocalDate dataNascimento = Utilidades.formatarDataLocalDate(dataNascimentoFormatoBR);
        Boolean fidelidadeBooleana = fidelidade.equals("sim");

        Cliente cliente = new Cliente(
                nomeCompleto,
                dataNascimento,
                documento,
                pais,
                estado,
                cidade,
                fidelidadeBooleana,
                observacao
        );

        if(clienteDAO.inserir(cliente)) {
            return "Cliente cadastrado com sucesso!";
        } else {
            throw new ClienteException("Não foi possível cadastrar o cliente! Por favor, tente novamente.");
        }
    }

    public String alterar(
            String id,
            String nomeCompleto,
            String dataNascimentoFormatoBR,
            String documento,
            String pais,
            String estado,
            String cidade,
            String fidelidade,
            String observacao
    ) throws ClienteException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, nomeCompleto, dataNascimentoFormatoBR, documento, pais, estado, cidade, fidelidade);
        if(!mensagemErro.isEmpty()) throw new ClienteException(mensagemErro);


        LocalDate dataNascimento = Utilidades.formatarDataLocalDate(dataNascimentoFormatoBR);
        Long idNumerico = Long.parseLong(id);
        Cliente clienteAlteracao = clienteDAO.selecionarPorId(idNumerico);
        Boolean fidelidadeBooleana = fidelidade.equals("sim");

        Cliente cliente = new Cliente(
            clienteAlteracao.getIdPessoa(),
            nomeCompleto,
            dataNascimento,
            documento,
            pais,
            estado,
            cidade,
            idNumerico,
            fidelidadeBooleana,
            observacao
        );

        if(clienteDAO.atualizar(cliente)) {
            return "Dados alterados com sucesso!";
        } else {
            throw new ClienteException("Não foi possível alterar os dados!! Por favor, tente novamente.");
        }
    }

    public String excluir(String id) throws ClienteException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null, null, null, null, null);
        if(!mensagemErro.isEmpty()) throw new ClienteException(mensagemErro);

        Long idNumerico = Long.parseLong(id);
        if(clienteDAO.deletar(idNumerico)) {
            return "Dados excluídos com sucesso!";
        } else {
            throw new ClienteException("Não foi possível excluir os dados! Por favor, tente novamente.");
        }
    }

    // Métodos privados

    private String validarCampos(
            Funcionalidade funcionalidade,
            String id,
            String nomeCompleto,
            String dataNascimentoFormatoBR,
            String documento,
            String pais,
            String estado,
            String cidade,
            String fidelidade
    ) throws ClienteException {
        String erros = "";
        // Verificação de id
        if(funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if(!id.isEmpty()) {
                if (Utilidades.validarNumero(id)) {
                    Long idNumerico = Long.parseLong(id);
                    if(clienteDAO.selecionarPorId(idNumerico) == null) erros += "\n- Id não encontrado.";
                } else {
                    erros += "\n- Id inválido.";
                }
            } else {
                erros += "\n- Id é obrigatório.";
            }
        }

        // Verificação de outros campos
        if(funcionalidade == Funcionalidade.CADASTRAR || funcionalidade == Funcionalidade.ALTERAR) {
            // Nome completo
            if(nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
                erros += "\n- Nome completo é obrigatório.";
            }
            // Data de nascimento
            if(dataNascimentoFormatoBR == null || dataNascimentoFormatoBR.trim().isEmpty()) {
                erros += "\n- Data de nascimento é obrigatória.";
            } else if(!Utilidades.validarDataBR(dataNascimentoFormatoBR)) {
                erros += "\n- Data de nascimento inválida.";
            }
            // Documento
            if(documento == null || documento.trim().isEmpty()) {
                erros += "\n- Documento é obrigatório.";
            }

            if(fidelidade == null || fidelidade.trim().isEmpty()) {
                erros += "\n- Fidelidade é obrigatória.";
            }

            if(!documento.isEmpty()) {
                Cliente clienteDocumento = clienteDAO.selecionarPorDocumento(documento);
                if(clienteDocumento != null) {
                    if(funcionalidade == Funcionalidade.ALTERAR) {
                        if(Utilidades.validarNumero(id)) {
                            Long idNumerico = Long.parseLong(id);
                            if(!clienteDocumento.getId().equals(idNumerico)) erros += "\n- Documento já existente.";
                        }
                    } else {
                        erros += "\n- Documento já existente.";
                    }
                }
            }
        }

        // Montagem da mensagem de erro
        String mensagemErro = "";
        if(!erros.isEmpty()) {
            mensagemErro = "Não foi possível " + funcionalidade.name().toLowerCase() + " o cliente! " +
                    "Erro(s) encontrado(s):" + erros;
        }
        return mensagemErro;
    }

}