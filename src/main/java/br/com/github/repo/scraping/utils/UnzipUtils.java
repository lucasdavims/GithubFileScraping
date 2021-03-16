package br.com.github.repo.scraping.utils;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import br.com.github.repo.scraping.dto.GithubFileDTO;
import br.com.github.repo.scraping.exception.RepositoryException;

public class UnzipUtils {

	public static List<GithubFileDTO> unzipFilesFromStream(String url, String user, String name, String lastCommit) throws Exception {
		
		URL urlContent = new URL(url);

		try ( InputStream inputStream = urlContent.openStream() ){
			
		    ZipInputStream zipIn = new ZipInputStream(inputStream);

			return mountFilesWithZipStream(zipIn);
		
		} catch (Exception e) {
			throw new RepositoryException("Git repository not found!");
		}
		 
	}
	
	private static List<GithubFileDTO> mountFilesWithZipStream(ZipInputStream zipIn) throws IOException {
		List<GithubFileDTO> files = new ArrayList<GithubFileDTO>();
		
		ZipEntry entry;
		
		 //while files exist, mount the dto
	    while ((entry = zipIn.getNextEntry()) != null) {
	    	if(entry.getSize() > 0) {
		    	
		    	files.add(GithubFileDTO.builder()
		    								.description(entry.getName())
		    								.extension(getFileExtentions(entry.getName().split("/")))
		    								.size(entry.getSize())
		    								.amountLines(countLines(zipIn))
		    						   .build());	
	    	}
	    }
		
		return files;
	}
	
	private static String getFileExtentions (String[] path) {

		List<String> file = Arrays.asList(path[path.length - 1].split("[.]"));
		
		if (file.size() >= 2) {
			return file.get(file.size() -1);
		}else {
			return " ";
		}
		
	}
	
	private static Long countLines(ZipInputStream  zipIn) throws IOException {
		
		InputStream is = new FilterInputStream(zipIn) {
            @Override
            public void close() throws IOException {
                zipIn.closeEntry();
            }
        };
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));		
		
		return reader.lines().count();
	}
}
