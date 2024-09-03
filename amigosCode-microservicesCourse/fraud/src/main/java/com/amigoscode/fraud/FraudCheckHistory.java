package com.amigoscode.fraud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FraudCheckHistory {
    @Id
    @SequenceGenerator(
            name="fraud-id-sequence",
            sequenceName = "fraudIdSequence"
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "fraudIdSequence")

    private Integer id;
    private Integer customerId;
    private boolean isFraudster;
    private LocalDateTime createAt;

}
