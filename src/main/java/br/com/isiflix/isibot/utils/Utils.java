package br.com.isiflix.isibot.utils;

import static dev.langchain4j.internal.Utils.getOrDefault;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class Utils {

	public static final String OPENAI_API_KEY = getOrDefault(System.getenv("OPENAI_API_KEY"), "demo");

	public static PathMatcher glob(String glob) {
		return FileSystems.getDefault().getPathMatcher("glob:" + glob);
	}

	public static Path toPath(String relativePath) {
		try {
			URL fileUrl = Utils.class.getClassLoader().getResource(relativePath);
			return Paths.get(fileUrl.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
