package expense_tracker_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils(){
    }
    public static Long currentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !(auth.getPrincipal() instanceof Long userId)){
            throw new IllegalStateException("Unauthenticated");
        }
        return userId;
    }
}
