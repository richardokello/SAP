package co.ke.spsat.bowip.user.dto;

import co.ke.spsat.bowip.user.Roles;

public record SignUpDTO(String login,
                        String password,
                        Roles role) {
}
