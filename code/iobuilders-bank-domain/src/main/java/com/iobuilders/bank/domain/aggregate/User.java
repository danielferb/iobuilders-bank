package com.iobuilders.bank.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;

	private String name;

	private String email;

	private String fiscalAddress;

	private String nif;

}