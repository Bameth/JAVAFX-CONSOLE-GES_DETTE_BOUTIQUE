package org.example.data.core.interfaces;

import org.example.data.core.config.Repository;
import org.example.data.entities.Client;

import java.util.List;

public interface ClientRepository extends Repository<Client> {
    Client selectByPhone(String phone);
    List<Client> findAllClientWithAccount();
    Client selectByUserId(int userId);
}
