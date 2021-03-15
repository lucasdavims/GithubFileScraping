package br.com.github.repo.scraping.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;



import br.com.github.repo.scraping.model.GithubFile;
import br.com.github.repo.scraping.model.GithubProject;
import br.com.github.repo.scraping.repository.GithubProjectRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GithubProjectResourceUnitTest {

	@Autowired
	private GithubProjectRepository repository;
	
	private String userGithub;
	private String nameGithub;
	
	private GithubProject project;
	private static final List<String> fileExtentions = Arrays.asList(new String[] {"java","txt","yml","mvnw","properties","cmd","xml"});
	
	Random random = new Random();
	
	@Before
	public void setup() {
		userGithub = "lucasdavims";
		nameGithub = "TAGPASSENGER";
		
		List<GithubFile> files = new ArrayList<>();
		
		while(files.size() <= 30) {
			files.add(GithubFile.builder()
									.amountLines(Long.valueOf(random.nextInt(999999999)))
									.description("File generated for automatized tests")
									.extension(fileExtentions.get(random.nextInt(7)))
									.size(Long.valueOf(random.nextInt(999999999)))
								.build());
		}
		
		project = GithubProject.builder()
								  .amountLines(String.valueOf(Long.valueOf(random.nextInt(999999999))))
								  .amoutBytes(String.valueOf(Long.valueOf(random.nextInt(999999999))))
								  .lastCommit("Fake commit generated for automatized tests")
								  .nameGithub(nameGithub)
								  .userGithub(userGithub)
								  .files(files)
							  .build();
		
		project = repository.save(project);
		
	}
	
	@Test
	public void testeRepo() {
		GithubProject projectRecover = repository.findByUserGithubAndNameGithub(userGithub, nameGithub);
		
		Assert.assertEquals(projectRecover.getId(), project.getId());
	}
	
}
