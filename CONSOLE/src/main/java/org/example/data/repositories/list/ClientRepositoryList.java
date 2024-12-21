package org.example.data.repositories.list;

import org.example.data.core.config.RepositoryListImpl;
import org.example.data.core.interfaces.ClientRepository;
import org.example.data.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryList extends RepositoryListImpl<Client> implements ClientRepository {
    private final List<Client> clientList = new ArrayList<>();

    public Client selectByPhone(String phone) {
        return clientList.stream().filter(client -> client.getPhone().compareTo(phone) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Client> findAllClientWithAccount() {
        return clientList.stream()
               .filter(client -> client.getUser()!= null)
               .collect(Collectors.toList());
    }

    @Override
    public Client selectByUserId(int userId) {
        return clientList.stream()
                .filter(client -> client.getUser().getId() == userId)
                .findFirst()
                .orElse(null);
    }
}
