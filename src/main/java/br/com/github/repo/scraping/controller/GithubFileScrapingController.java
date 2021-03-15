package br.com.github.repo.scraping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.repo.scraping.dto.GithubProjectDTO;
import br.com.github.repo.scraping.exception.RepositoryException;
import br.com.github.repo.scraping.service.GithubFileScrapingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/githubFileInfoScraping")
@Api(value="/githubFileInfoScraping",  tags="githubFileInfoScraping")
public class GithubFileScrapingController {
	
	@Autowired
	private GithubFileScrapingService service;
	
	@ApiOperation("Get all files and folders details from a specific Github repository if it was not loaded already and return the number of files and the total number of bytes of all the files grouped by file extension")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = GithubProjectDTO.class ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@GetMapping(value="/search")
	public ResponseEntity<?> findByRepository(@RequestParam(name = "user") String user, @RequestParam(name = "name") String name) throws RepositoryException {
		
		GithubProjectDTO project = service.searchAndScrapRepoFiles(user, name);
		
		return ResponseEntity.ok().body(project);
		
	}

}
