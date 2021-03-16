package br.com.github.repo.scraping.dto;

import java.io.Serializable;
import java.util.List;

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
public class GithubProjectDTO implements Serializable{

	private static final long serialVersionUID = -4752754799170569584L;
	
	private String userGithub;
	private String nameGithub;
	private String amoutBytes;
	private String amountLines;
	
	@JsonIgnore
	private String lastCommit;
	
	@JsonIgnore
	private List<GithubFileDTO> files;
	
	private List<GithubFileGroupDTO> filesGroup;
}
