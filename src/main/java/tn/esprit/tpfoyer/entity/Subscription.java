package tn.esprit.tpfoyer.entity;
import jakarta.persistence.*;

import java.time.LocalDate;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long numSub;
    LocalDate startDate;
    LocalDate endDate;
    Float price;
    TypeSubscription typeSub;
    //	@Enumerated(EnumType.STRING)


}