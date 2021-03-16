package br.com.github.repo.scraping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.github.repo.scraping.dto.GithubFileDTO;
import br.com.github.repo.scraping.dto.GithubFileGroupDTO;
import br.com.github.repo.scraping.dto.GithubProjectDTO;
import br.com.github.repo.scraping.exception.RepositoryException;
import br.com.github.repo.scraping.model.GithubProject;
import br.com.github.repo.scraping.repository.GithubProjectRepository;
import br.com.github.repo.scraping.service.GithubFileScrapingService;
import br.com.github.repo.scraping.utils.HtmlUtils;
import br.com.github.repo.scraping.utils.UnzipUtils;

@Service
public class GithubFileScrapingServiceImpl implements GithubFileScrapingService {

	@Autowired
    private GithubProjectRepository projectRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public GithubProjectDTO searchAndScrapRepoFiles(String user, String name) throws RepositoryException {

		try {
			
			GithubProjectDTO project = null;
			
			//search the files in the project database
			GithubProject projectRecovered = findProjectInfos(user, name);
		
			//find last commit from github repository with name and user
			String lastCommit = getLastCommit(user, name);
					
			//if the file already exists in the database and is updated, it returns
			if (projectRecovered != null && projectRecovered.getLastCommit().equals(lastCommit)) {
				
				project = mapper.map(projectRecovered, GithubProjectDTO.class);
				
			}else {
				
				//search files into repository github
				List<GithubFileDTO> listFiles = UnzipUtils.unzipFilesFromStream(getUrlGithubProjectZipFolderMaster(user, name), user, name, lastCommit);
				
				project = mountProjectDTO(user, name, lastCommit, listFiles);
			
				if(projectRecovered != null)
					delete(projectRecovered);
			
				//save on database
				saveWithDTO(project);					
				
			}
			
			return groupByFileTipes(project);
			
		} catch (Exception e) {
			throw new RepositoryException("Error fetching files in git or not found!");
		}
		
	}
	
	private GithubProjectDTO mountProjectDTO(String user, String name, String lastCommit, List<GithubFileDTO> listFiles) {
		
		Long amountSize = listFiles.stream().collect(Collectors.summingLong(o -> o.getSize()));
		Long amountLines = listFiles.stream().collect(Collectors.summingLong(o -> o.getAmountLines()));
		
		return GithubProjectDTO.builder()
									.userGithub(user)
									.nameGithub(name)
									.lastCommit(lastCommit)
									.amountLines(String.valueOf(amountLines))
									.amoutBytes(String.valueOf(amountSize))
									.files(listFiles)
								 .build();
	}
	
	private GithubProjectDTO groupByFileTipes(GithubProjectDTO project) {
		
		Map<String, List<GithubFileDTO>>  filesGroups = project.getFiles().stream().collect(Collectors.groupingBy(w -> w.getExtension()));
		
		List<GithubFileGroupDTO> list = new ArrayList<>();
		
		filesGroups.forEach((key, value)-> {
			
			Long size = value.stream().collect(Collectors.summingLong(o -> o.getSize()));
			Long amountLines = value.stream().collect(Collectors.summingLong(o -> o.getAmountLines()));
			
			list.add(GithubFileGroupDTO.builder()
											.amountLines(amountLines)
											.size(size)
											.extension(key)
										.build());
		});
		
		project.setFilesGroup(list);
		
		return project;
	}


	private GithubProject findProjectInfos(String user, String name) {
		GithubProject project = projectRepository.findByUserGithubAndNameGithub(user, name);
		
		return project;
	 
	}
	
	private void saveWithDTO(GithubProjectDTO project) {
		//convert DTO to entity for save the project on database
		GithubProject projectEntity = mapper.map(project, GithubProject.class);
		
		projectRepository.save(projectEntity);
	}
	
	private void delete(GithubProject projectEntity) {
		projectRepository.delete(projectEntity);
	}
	
	private String getLastCommit(String user, String name) throws RepositoryException {
		try {
			return HtmlUtils.getLastCommitFromRepositoryWithURL(getUrlGithubProjectCommits(user,name));
		} catch (Exception e) {
			throw new RepositoryException("Git repository not found!");
		}
	
	}
	
	private String getUrlGithubProjectCommits(String user, String name) {
		return "https://github.com/"+user+"/"+name+"/commits/master";
	}
	
	private String getUrlGithubProjectZipFolderMaster(String user, String name) {
		return "https://github.com/"+user+"/"+name+"/archive/master.zip";
	}

}
