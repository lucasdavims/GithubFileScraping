package br.com.github.repo.scraping.dto;

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
public class GithubFileGroupDTO {
	
	private Long size;
	private Long amountLines;
	private String extension;
	
}
