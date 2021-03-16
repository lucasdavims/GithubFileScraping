package br.com.github.repo.scraping.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "github_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubFile implements Serializable{

	private static final long serialVersionUID = -6060360815022438105L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "size", nullable = false)
	private Long size;
	
	@Column(name = "amount_lines", nullable = false)
	private Long amountLines;
	
	@Column(name = "extension", nullable = false)
	private String extension;
	
	@ManyToOne(fetch = FetchType.LAZY,  optional = false, cascade={ CascadeType.ALL})
    @JoinColumn(name = "project_id")
    private GithubProject project;
}
