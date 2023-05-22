package uz.jaloliddin.bankomat.domain;

import lombok.Data;
import uz.jaloliddin.bankomat.domain.enumeration.RoleName;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
