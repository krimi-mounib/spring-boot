package tn.esprit.mohamedmounibkrimicce11.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tn.esprit.mohamedmounibkrimicce11.domain.Agence;
import tn.esprit.mohamedmounibkrimicce11.domain.CategorieVehicule;
import tn.esprit.mohamedmounibkrimicce11.domain.Client;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import tn.esprit.mohamedmounibkrimicce11.domain.StatutReservation;
import tn.esprit.mohamedmounibkrimicce11.domain.StatutVehicule;
import tn.esprit.mohamedmounibkrimicce11.domain.Vehicule;
import tn.esprit.mohamedmounibkrimicce11.repository.IAgenceRepository;
import tn.esprit.mohamedmounibkrimicce11.repository.IClientRepository;
import tn.esprit.mohamedmounibkrimicce11.repository.IReservationRepository;
import tn.esprit.mohamedmounibkrimicce11.repository.IVehiculeRepository;

/** Données jetables uniquement pour le profil demo ; jamais utilisées en production. */
@Configuration
@Profile("demo")
public class DemoDataInitializer {

    @Bean
    CommandLineRunner seedDemoData(
            IAgenceRepository agences,
            IClientRepository clients,
            IVehiculeRepository vehicules,
            IReservationRepository reservations) {
        return args -> {
            if (reservations.count() > 0) {
                System.out.println("[DEMO] Données déjà présentes ; aucun seed supplémentaire.");
                return;
            }

            Agence agence = new Agence();
            agence.setNom("AutoLoc Demo Centre");
            agence.setVille("Tunis");
            agence.setAdresse("Avenue de démonstration");
            agence.setTelephone("71111222");
            agence = agences.save(agence);

            Client client = new Client();
            client.setNom("Ben Ali");
            client.setPrenom("Sami");
            client.setEmail("sami.demo@autoloc.local");
            client.setTelephone("20111222");
            client.setNumPermis("DEMO-PERMIS-001");
            client.setDateInscription(LocalDate.now());
            client = clients.save(client);

            Vehicule vehicule = new Vehicule();
            vehicule.setImmatriculation("DEMO-120-TN");
            vehicule.setMarque("Toyota");
            vehicule.setModele("Yaris");
            vehicule.setCategorie(CategorieVehicule.CITADINE);
            vehicule.setTarifJournalier(new BigDecimal("120.00"));
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
            vehicule.setAgence(agence);
            vehicule = vehicules.save(vehicule);

            LocalDate debut = LocalDate.now().plusDays(1);
            Reservation reservation = new Reservation();
            reservation.setDateDebut(debut);
            reservation.setDateFin(debut.plusDays(6)); // 7 jours, borne inclusive
            reservation.setStatut(StatutReservation.EN_ATTENTE);
            reservation.setVehicule(vehicule);
            reservation.setClient(client);
            reservations.save(reservation);

            System.out.println("[DEMO] Jeu de données créé : agenceId=" + agence.getIdAgence()
                    + ", clientId=" + client.getIdClient()
                    + ", vehiculeId=" + vehicule.getIdVehicule()
                    + ", reservationId réservé à l'identifiant généré par la base.");
            System.out.println("[DEMO] Vérifier l'identifiant exact avec GET /api/reservations.");
        };
    }
}
