package SOAP;

import jakarta.jws.WebService;

@WebService
public interface Validator {
    public boolean isRegisteredProfessor(String name, String surname);
}
