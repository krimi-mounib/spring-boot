package tn.esprit.mohamedmounibkrimicce11.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable avec l'identifiant " + id);
    }
}
