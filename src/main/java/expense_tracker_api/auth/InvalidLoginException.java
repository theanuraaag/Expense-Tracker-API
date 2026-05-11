package expense_tracker_api.auth;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(){
        super("Invalid email or password");
    }
}
