package br.com.github.repo.scraping.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.github.repo.scraping.controller.GithubFileScrapingController;
import br.com.github.repo.scraping.dto.GithubFileDTO;
import br.com.github.repo.scraping.dto.GithubProjectDTO;
import br.com.github.repo.scraping.service.GithubFileScrapingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { GithubFileScrapingController.class})
public class GithubFileScrapingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GithubFileScrapingService service;
	
	private String userGithub;
	private String nameGithub;
	private static final String URL = "/githubFileInfoScraping/search";
	
	private GithubProjectDTO project;
	private static final List<String> fileExtentions = Arrays.asList(new String[] {"java","txt","yml","mvnw","properties","cmd","xml"});
	
	Random random = new Random();
	
	@Before
	public void setup() throws Exception {
		userGithub = "lucasdavims";
		nameGithub = "TAGPASSENGER";
		
		List<GithubFileDTO> files = new ArrayList<>();
		
		while(files.size() <= 100) {
			files.add(GithubFileDTO.builder()
										.amountLines(Long.valueOf(random.nextInt(999999999)))
										.description("File generated for automatized tests")
										.extension(fileExtentions.get(random.nextInt(7)))
										.size(Long.valueOf(random.nextInt(999999999)))
									.build());
		}
		
		project = GithubProjectDTO.builder()
									  .amountLines(String.valueOf(Long.valueOf(random.nextInt(999999999))))
									  .amoutBytes(String.valueOf(Long.valueOf(random.nextInt(999999999))))
									  .lastCommit("Fake commit generated for automatized tests")
									  .nameGithub(nameGithub)
									  .userGithub(userGithub)
									  .files(files)
								  .build();
		
	}
	
	@Test
	public void getProjectInfos() throws Exception {
		Mockito.when(service.searchAndScrapRepoFiles(userGithub, nameGithub)).thenReturn(project);
		
		String response = asJson(project);

		mockMvc.perform(get(URL+"?user="+userGithub+"&name="+nameGithub).contentType(MediaType.APPLICATION_JSON).content(response))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(service).searchAndScrapRepoFiles(userGithub, nameGithub);
	
	}

	public String asJson(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
