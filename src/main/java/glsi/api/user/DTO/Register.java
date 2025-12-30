package glsi.api.user.DTO;

public record Register(
        String firstName,
        String lastName,
        String email,
        String password
) { }
