package org.example.data.controllers;

import org.example.data.entities.Client;
import org.example.data.services.ClientServiceImpl;
import org.example.data.views.ClientView;

public class ClientController {
    private final ClientServiceImpl clientService;
    private final ClientView clientView;

    public ClientController(ClientServiceImpl clientService, ClientView clientView) {
        this.clientService = clientService;
        this.clientView = clientView;
    }

    public void createClient() {
        Client client = clientView.saisie();
        clientService.create(client);
    }
}

