// this web crawler use BFS (Breadth First Search) for crawling the web pages

import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;


public class WebCrawler {
	
	// used for BFS queue which uses FIFO principle
	private Queue<String> queue;

	// used for checking the site is already visited or not
	private List<String> discoveredWebsiteList;

	public WebCrawler(){
		// initialize the data member
		queue = new LinkedList<>();
		discoveredWebsiteList = new ArrayList<>();
	}

	// @param root 
	// form the site where we want to start our search
	public void discoverWeb(String root){
		queue.add(root);
		discoveredWebsiteList.add(root);

		// till the queue is empty search for the other website
		while(!queue.isEmpty()){
			// remove the first site from the queue
			String v = queue.remove();

			// reading the whole html or the given site
			String rawHtml = readHtml(v);

			String regexp = "http://(\\w+\\.)*(\\w+)";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(rawHtml);

			while(matcher.find()){
				String actualUrl = matcher.group();

				if(!discoveredWebsiteList.contains(actualUrl)){
					queue.add(actualUrl);
					discoveredWebsiteList.add(actualUrl);
					System.out.println("WebSite found with URL: "+ actualUrl);
				}
			}
		}
	}

	// @param root 
	// form the site where we want to serch the more website
	private String readHtml(String site){
		String rawHtml = "";

		try{
			URL url = new URL(site);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine = "";

			while((inputLine = br.readLine()) != null){
				rawHtml += inputLine;
			}

			br.close();
		}catch(Exception e){
			System.out.println("Can't reach the site.");
			e.printStackTrace();
		}
		return rawHtml;
	}
}