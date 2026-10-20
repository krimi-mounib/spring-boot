package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Client;

public interface IClientService {
    Client create(Client entity);
    Client findById(Long id);
    List<Client> findAll();
    Client update(Long id, Client entity);
    void deleteById(Long id);
}
