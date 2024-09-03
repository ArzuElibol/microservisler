package com.amigoscode.fraud.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
@SequenceGenerator(
        name="customer-id-sequence",
        sequenceName = "customerIdSequence"
)
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "customerIdSequence")

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

}
