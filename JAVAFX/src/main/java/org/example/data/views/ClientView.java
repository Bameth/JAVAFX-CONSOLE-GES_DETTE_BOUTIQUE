package org.example.data.views;

import java.util.Scanner;

import org.example.data.core.interfaces.ViewImpl;
import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;

public class ClientView extends ViewImpl<Client> {
    private final ClientServiceImpl clientService;
    private final UserServiceImpl userService;
    private final UserView userView;
    Client client = new Client();

    public ClientView(Scanner scanner, ClientServiceImpl clientService, UserServiceImpl userService,
            UserView userView) {
        super(scanner);
        this.clientService = clientService;
        this.userService = userService;
        this.userView = userView;
    }

    @Override
    public Client saisie() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("Enter surname: ");
        client.setSurname(scanner.nextLine());

        System.out.println("Enter phone: ");
        client.setPhone(scanner.nextLine());

        System.out.println("Enter address: ");
        client.setAddress(scanner.nextLine());

        return askForUserClient();
    }

    public Client askForUserClient() {
        System.out.println("Voulez vous creer un compte utilisateur a ce Client? (oui/non)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("oui")) {
            User user = userView.saisie();
            userService.create(user);
            client.setUser(user);
        } else {
            client.setUser(null);
        }
        return client;
    }
}
