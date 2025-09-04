package br.com.isiflix.isibot.utils;

import static dev.langchain4j.internal.Utils.getOrDefault;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<String> splitMessage(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            result.add("");
            return result;
        }

        int maxLength = 2000;
        int length = input.length();
        
        // Se a string for menor ou igual a 2000 caracteres, retorna ela como único elemento
        if (length <= maxLength) {
            result.add(input);
            return result;
        }

        // Subdivide a string em partes de até 2000 caracteres
        for (int i = 0; i < length; i += maxLength) {
            int end = Math.min(i + maxLength, length);
            result.add(input.substring(i, end));
        }
        
        return result;
    }
}


