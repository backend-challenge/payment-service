package com.invillia.acme.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity(name = "tb_item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String description;
	@Column
	private BigDecimal unitPrice;
	@Column
	private Integer quantity;
}
