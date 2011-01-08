package info.yalamanchili.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

public class SearchUtils {
	private static final Log log = LogFactory.getLog(SearchUtils.class);

	public static Query getLuceneQuery(String searchText, String defaultField,
			StandardAnalyzer analyzer, String... fields) {
		log.debug("SearchString:" + searchText);
		QueryParser parser = new QueryParser(searchText, analyzer);
		org.apache.lucene.search.Query luceneQuery;
		String searchQuery = SearchUtils.getSearchQuery(searchText, fields)
				.trim();
		log.debug("SearchQuery:" + searchQuery);
		try {
			luceneQuery = parser.parse(searchQuery);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		}
		return luceneQuery;
	}

	/*
	 * this is a temp method to get around.
	 * http://littlesquare.com/2010/05/upgrading
	 * -to-hibernate-search-3-2-0-w-seam/
	 */
	public static FullTextSession getFullTextSession(EntityManager em) {
		Session session = (Session) em.getDelegate();
		return Search.getFullTextSession(session.getSessionFactory()
				.getCurrentSession());
	}

	// TODO fix issue with empty string
	/**
	 * Caused by: org.apache.lucene.queryParser.ParseException: Cannot parse '':
	 * Encountered "<EOF>" at line 1, column 0. Was expecting one of: <NOT> ...
	 * "+" ... "-" ... "(" ... "*" ... <QUOTED> ... <TERM> ... <PREFIXTERM> ...
	 */
	public static String getSearchQuery(String searchText, String... fields) {
		StringBuilder searchQuery = new StringBuilder();
		for (String word : splitSearchString(searchText, ' ')) {
			for (String field : fields) {
				searchQuery.append(field);
				searchQuery.append(":");
				searchQuery.append(word);
				searchQuery.append(" ");
			}
		}
		return searchQuery.toString();
	}

	public static List<String> splitSearchString(String searchText,
			char seperator) {
		List<String> words = new ArrayList<String>();
		Scanner searchTextScanner = new Scanner(searchText);
		while (searchTextScanner.hasNext()) {
			words.add(searchTextScanner.next());
		}
		return words;
	}
}