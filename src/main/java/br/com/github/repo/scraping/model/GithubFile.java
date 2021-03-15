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
	@Column(name = "id")
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "size")
	private Long size;
	
	@Column(name = "amount_lines")
	private Long amountLines;
	
	@Column(name = "extension")
	private String extension;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade={ CascadeType.ALL})
    @JoinColumn(name = "project_id")
    private GithubProject project;
}
