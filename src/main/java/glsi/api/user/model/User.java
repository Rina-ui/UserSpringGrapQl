package glsi.api.user.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String sexe;
    private Integer age;

    @Column(unique = true)
    private String email;
    private String password;
}

