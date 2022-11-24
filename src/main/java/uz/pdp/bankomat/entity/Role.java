package uz.pdp.bankomat.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uz.pdp.bankomat.entity.enums.RoleName;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;


}
