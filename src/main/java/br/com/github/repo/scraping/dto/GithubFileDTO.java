package br.com.github.repo.scraping.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubFileDTO implements Serializable{

	private static final long serialVersionUID = 6036578973253310766L;
	
	@JsonIgnore
	private String description;
	private Long size;
	private Long amountLines;
	private String extension;
}
