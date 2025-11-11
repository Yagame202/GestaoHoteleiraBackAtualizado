package core.controller;

import base.enumeration.Funcionalidade;
import base.exception.ClienteException;
import base.exception.PessoaException;
import core.test.ClienteTest;
import core.test.PessoaTest;

import java.util.Scanner;

public class ClienteController {

    // Atributos
    private ClienteTest clienteTest;

    // Construtor
    public ClienteController(ClienteTest clienteTest) {
        this.clienteTest = clienteTest;
    }

    // Gerenciador de testes
    public void iniciar() throws ClienteException {
        Scanner entrada = new Scanner(System.in);
        String opcao = null;

        System.out.println(
                "=== TESTE MODULO CLIENTE ===\n"
                        + "1 - Listar\n"
                        + "2 - Cadastrar\n"
                        + "3 - Alterar\n"
                        + "4 - Excluir"
        );

        do {
            System.out.println("\nEscolha a funcionalidade:");
            opcao = entrada.nextLine();

            Funcionalidade funcionalidade = null;
            switch (opcao) {
                case "1":
                    funcionalidade = Funcionalidade.LISTAR;
                    break;
                case "2":
                    funcionalidade = Funcionalidade.CADASTRAR;
                    break;
                case "3":
                    funcionalidade = Funcionalidade.ALTERAR;
                    break;
                case "4":
                    funcionalidade = Funcionalidade.EXCLUIR;
                    break;
            }

            if(funcionalidade != null) {
                try {
                    System.out.println("Funcionalidade: " + funcionalidade);
                    System.out.println(clienteTest.testar(funcionalidade));
                } catch(ClienteException excecao) {
                    System.err.println(excecao.getMessage());
                }
            }

        } while(!opcao.isEmpty());
    }

}
