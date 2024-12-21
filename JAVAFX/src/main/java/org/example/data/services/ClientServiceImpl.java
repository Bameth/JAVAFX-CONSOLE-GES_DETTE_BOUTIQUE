package org.example.data.services;

import org.example.data.core.interfaces.ClientRepository;
import org.example.data.entities.Client;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public void create(Client client) {
        clientRepository.insert(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.selectAll();
    }

    @Override
    public Client search(String phone) {
        return clientRepository.selectByPhone(phone);
    }
    @Override
    public boolean update(Client objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public Client getBy(String objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }
    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
    @Override
    public List<Client> findAllClientWithAccount() {
        return clientRepository.findAllClientWithAccount();
    }
    @Override
    public Client findById(int id) {
        return clientRepository.selectById(id);
    }
    @Override
    public Client findByUserId(int userId) {
        return clientRepository.selectByUserId(userId);
    }    
}