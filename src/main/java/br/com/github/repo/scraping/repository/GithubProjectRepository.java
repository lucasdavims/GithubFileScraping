package br.com.github.repo.scraping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.github.repo.scraping.model.GithubProject;

@Repository
public interface GithubProjectRepository extends JpaRepository<GithubProject, Long>{

	GithubProject findByUserGithubAndNameGithub(String userGithub, String nameGithub);
}
