package com.invillia.acme.model.db;

import com.invillia.acme.model.dto.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Column
	private String numberCreditCard;

	@Column
	private LocalDate paymentDate;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	private Order orderE;

}
