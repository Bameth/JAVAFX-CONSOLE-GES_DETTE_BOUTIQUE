package org.example.data.services;

import org.example.data.entities.Client;

import org.example.data.core.interfaces.Service;

import java.util.List;

public interface ClientService extends Service<Client>  {
    public Client search(String phone) ;
    public Client findById(int id) ;
    List<Client> findAllClientWithAccount();
    Client findByUserId(int userId);
}
