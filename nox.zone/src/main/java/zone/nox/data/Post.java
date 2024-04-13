package zone.nox.data;

import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public record Post(
		String title, String summary, LocalDateTime date,
		Optional<String> localVideo, Optional<String> youTubeId, List<Element> content)
		implements Document.Data {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmm");

	public static Post fromMd(Md post) {
		var date = LocalDateTime.parse(post.date(), DATE_FORMAT);
		var content = post.contentParsedAsMarkdown();
		return new Post(post.title(), post.summary(), date, post.localVideo(), post.youTubeId(), content);
	}

	public record Md(
			String title, String summary, String date,
			Optional<String> localVideo, Optional<String> youTubeId, List<Element> contentParsedAsMarkdown)
			implements Document.Data { }

}
