package com.SL.SportyShoes.student;

import java.time.LocalDate;
import java.time.Period;

import lombok.Data;

@Data
public class StudentDTO {
	//private String password;
	private String name;
	private LocalDate dob;
	private Integer age;
	private String email;
	
	public StudentDTO(Student studentObj) {
		this.name = studentObj.getName();
		this.dob = studentObj.getDob();
		this.email = studentObj.getEmail();
		//this.password = studentObj.getPassword();
	}
 	
	public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
