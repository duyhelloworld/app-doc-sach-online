package huce.edu.vn.appdocsach.services.impl.auth.users;

import huce.edu.vn.appdocsach.entities.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClaimValues {
    private String username;
    private String email;
    private Role role;
}
