import base.exception.ClienteException;
import core.controller.ClienteController;
import core.dao.ClienteDAO;
import core.service.ClienteService;
import core.test.ClienteTest;

public class PrincipalCliente {

    public static void main(String[] args) throws ClienteException {
        // Inicialização de objetos
        ClienteDAO clienteDAO = new ClienteDAO();
        ClienteService clienteService = new ClienteService(clienteDAO);
        ClienteTest clienteTest = new ClienteTest(clienteService);
        ClienteController clienteController = new ClienteController(clienteTest);
        clienteController.iniciar();
    }
}
