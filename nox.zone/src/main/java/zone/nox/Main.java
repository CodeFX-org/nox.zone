package zone.nox;

import dev.nipafx.ginevra.Ginevra;
import dev.nipafx.ginevra.outline.GeneralDocument;
import zone.nox.data.Post;
import zone.nox.templates.LandingTemplate;
import zone.nox.templates.PostTemplate;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Main {

	private static final Path POSTS = Path.of("nox.zone/src/main/resources/posts").toAbsolutePath();
	private static final Path RESOURCES = Path.of("nox.zone/src/main/resources/resources").toAbsolutePath();
	private static final Path VIDEOS = Path.of("nox.zone/src/main/resources/videos").toAbsolutePath();

	public record Config(Optional<String> target) { }

	public static void main(String[] args) {
		var ginevraWithConfig = Ginevra.initialize(args, Config.class);
		var outliner = ginevraWithConfig.ginevra().newOutliner();

		var resources = outliner.sourceBinaryFiles("resources", RESOURCES);
		outliner.storeResource(resources);
		var videos = outliner.sourceBinaryFiles("videos", VIDEOS);
		outliner.storeResource(videos);

		var content = outliner.sourceTextFiles("posts", POSTS);
		var markdown = outliner.transformMarkdown(content, Post.Md.class);
		var posts = outliner.transform(markdown, postMd -> List.of(new GeneralDocument<>(
				postMd.id().transform("parsed"),
				Post.fromMd(postMd.data()))));
		outliner.store(posts, "posts");

		outliner.generate(new LandingTemplate());
		outliner.generate(new PostTemplate(Target.from(ginevraWithConfig.config())));

		outliner.build().run();
	}

}
