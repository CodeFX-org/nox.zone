package zone.nox.data;

import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.Document;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

public record Post(
		String title, String summary, Path slug, Integer index, String name, LocalDateTime date,
		Optional<String> localFile, Optional<String> youTubeId, List<Element> content)
		implements Document.Data {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmm");

	public static Post fromMd(Md post) {
		var fileNameParts = getFileNameParts(post);
		var index = Integer.parseInt(fileNameParts[0]);
		var name = fileNameParts[2];
		var slug = Path.of("%03d".formatted(index));
		var date = LocalDateTime.parse(fileNameParts[1], DATE_FORMAT);
		var localFile = post
				.localFile()
				.or(() -> Optional.of("%03d-%s".formatted(index, name)))
				// this allows the front matter to override the local file prefix
				// that is generated from the post's file name by explicitly
				// setting it to the empty string
				.filter(not(String::isBlank));
		var content = post.contentParsedAsMarkdown();
		return new Post(post.title(), post.summary(), slug, index, name, date, localFile, post.youTubeId(), content);
	}

	private static String[] getFileNameParts(Md post) {
		var fileName = post.file().getFileName().toString();
		var fileNameParts = fileName
				.substring(0, fileName.lastIndexOf("."))
				.split("--");
		if (fileNameParts.length != 3)
			throw new IllegalArgumentException();
		return fileNameParts;
	}

	public record Md(
			String title, String summary, Optional<String> localFile, Optional<String> youTubeId,
			Path file, List<Element> contentParsedAsMarkdown)
			implements Document.Data { }

}
