package com.globallogic.users.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Phone {

	private String number;
	private String citycode;
	private String countrycode;	

}
