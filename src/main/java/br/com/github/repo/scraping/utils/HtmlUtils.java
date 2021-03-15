package br.com.github.repo.scraping.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import br.com.github.repo.scraping.exception.RepositoryException;


public class HtmlUtils {

	public static String getLastCommitFromRepositoryWithURL(String url) throws RepositoryException, MalformedURLException, IOException{
		try {
			String html = getHtmlFromUrl(url);
			
			return searchCommitLinkIntoHtml(html, "d-none js-permalink-shortcut");
		} catch (Exception e) {
			throw new RepositoryException("Git repository not found!");
		}

	}
	
	private static String searchCommitLinkIntoHtml(String html, String selector) {
		int index = html.indexOf(selector);
		String commit = "";
		boolean foundStart = false;
		//start searching into html using index of selector
		while(index > 0) {
			//search start of tag
			if (html.charAt(index) == '<' || foundStart) {
				foundStart = true;
				
				//if start of tag was found, reverse navigation to check attributes for this tag
				String possibleAttr = Character.toString(html.charAt(index))+Character.toString(html.charAt(index+1))+Character.toString(html.charAt(index+2))+Character.toString(html.charAt(index+3))+"";
				//check if attribute is href to commit link
				if ("href".equals(possibleAttr)) {
					index +=6;
					//collect all text link
					while(index > 0) {
						if(html.charAt(index) != '"') {
							commit += html.charAt(index);
							index++;
						}else {
							index =-2;
						}
					}
				}
				index++;
			}
			if(!foundStart)
			index--;
		}
		
		return commit;
	}
	
	
	private static String getHtmlFromUrl(String url) throws MalformedURLException, IOException {
		URL urlContent = new URL(url);
		URLConnection yc = urlContent.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
		return in.lines().collect(Collectors.joining(" "));
		
	}
}
