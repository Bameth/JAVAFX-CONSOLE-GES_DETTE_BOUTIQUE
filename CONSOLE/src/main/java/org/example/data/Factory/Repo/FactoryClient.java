package org.example.data.Factory.Repo;

import org.example.data.core.interfaces.ClientRepository;
import org.example.data.repositories.DB.ClientRepositoryBd;
import org.example.data.repositories.DB.UserRepositoryBd;
import org.example.data.repositories.JPA.ClientRepositoryJPAImpl;
import org.example.data.repositories.list.ClientRepositoryList;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserService; // Importez UserService
import org.example.data.services.UserServiceImpl;

public class FactoryClient {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientServiceImpl;
    private ClientRepositoryList clientRepositoryList;
    private ClientRepositoryBd clientRepositoryBD;
    private UserRepositoryBd userRepositoryBD;
    private ClientRepositoryJPAImpl clientRepositoryJPAImpl;

    public FactoryClient() {
        userRepositoryBD = new UserRepositoryBd();
        ClientRepositoryBd clientRepositoryBd = new ClientRepositoryBd();
        ClientRepositoryList clientRepositoryList = new ClientRepositoryList();
        this.clientRepositoryJPAImpl = new ClientRepositoryJPAImpl(); 
        UserService userService = new UserServiceImpl(userRepositoryBD); 
        clientServiceImpl = new ClientServiceImpl(clientRepositoryJPAImpl); 
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public ClientServiceImpl getClientServiceImpl() {
        return clientServiceImpl;
    }

    public ClientRepositoryList getClientRepositoryList() {
        return clientRepositoryList;
    }

    public ClientRepositoryBd getClientRepositoryBD() {
        return clientRepositoryBD;
    }

    public UserRepositoryBd getUserRepositoryBD() {
        return userRepositoryBD;
    }

    public ClientRepositoryJPAImpl getClientRepositoryJPAImpl() {
        return clientRepositoryJPAImpl;
    }
}
