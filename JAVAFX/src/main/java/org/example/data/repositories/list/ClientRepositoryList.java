package org.example.data.repositories.list;

import org.example.data.core.interfaces.ClientRepository;
import org.example.data.core.config.RepositoryListImpl;
import org.example.data.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryList extends RepositoryListImpl<Client> implements ClientRepository {
    private final List<Client> clientList = new ArrayList<>();

    public Client selectByPhone(String phone) {
        return clientList.stream().filter(client -> client.getPhone().compareTo(phone) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Client> findAllClientWithAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByClientWithAccount'");
    }

    @Override
    public Client selectByUserId(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByUserId'");
    }

}