package br.com.github.repo.scraping.service;

import br.com.github.repo.scraping.dto.GithubProjectDTO;
import br.com.github.repo.scraping.exception.RepositoryException;

public interface GithubFileScrapingService {
	
	/**
	 * @param user
	 * @param name
	 * @return a github project with its files grouped by file type
	 * @throws RepositoryException
	 */
	public GithubProjectDTO searchAndScrapRepoFiles(String user, String name) throws RepositoryException ;

}
