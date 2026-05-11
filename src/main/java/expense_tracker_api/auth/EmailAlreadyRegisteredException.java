package expense_tracker_api.auth;

public class EmailAlreadyRegisteredException extends RuntimeException{
    private final String email;

    public EmailAlreadyRegisteredException(String email){
        super("Email already registered:" + email);
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
}
