package br.com.github.repo.scraping.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "github_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubProject  implements Serializable{

	private static final long serialVersionUID = 1110634407684678040L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "user_github", nullable = false)
	private String userGithub;
	
	@Column(name = "name_github", nullable = false)
	private String nameGithub;
	
	@Column(name = "last_commit", nullable = false)
	private String lastCommit;
	
	@Column(name = "amout_bytes", nullable = false)
	private String amoutBytes;
	
	@Column(name = "amount_lines", nullable = false)
	private String amountLines;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade={ CascadeType.ALL})
	private List<GithubFile> files;
	
	@PrePersist
	private void prePersist() {
		files.forEach( c -> c.setProject(this));
	}

}
