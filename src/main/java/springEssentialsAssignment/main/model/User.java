package springEssentialsAssignment.main.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(name = "name")
    private String name;
    @Column(name = "secondName")
    private String secondName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "birth")
    private Date birth;

}
