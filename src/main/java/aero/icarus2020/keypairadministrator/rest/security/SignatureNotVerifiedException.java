package aero.icarus2020.keypairadministrator.rest.security;

public class SignatureNotVerifiedException extends Exception {
    public SignatureNotVerifiedException(String message) {
        super(message);
    }
}
