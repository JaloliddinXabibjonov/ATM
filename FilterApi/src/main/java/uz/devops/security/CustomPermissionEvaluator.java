package uz.devops.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object accessType, Object permission) {
        if (authentication!=null){
            if ("hasAccess".equalsIgnoreCase(String.valueOf(accessType))){
                while (authentication.getAuthorities().iterator().hasNext()){
                    boolean validate = validatePermission(String.valueOf(permission), authentication.getAuthorities().iterator().next().getAuthority());
                    if (validate){
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean validatePermission(String permission, String roleName){
        return roleName.startsWith(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
