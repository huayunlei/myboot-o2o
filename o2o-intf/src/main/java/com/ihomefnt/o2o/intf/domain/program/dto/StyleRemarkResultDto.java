package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

@Data
public class StyleRemarkResultDto {
	
	private String question;
	
	private List<String> answers;
}
