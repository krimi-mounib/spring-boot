package tn.esprit.mohamedmounibkrimicce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedmounibkrimicce11.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}
