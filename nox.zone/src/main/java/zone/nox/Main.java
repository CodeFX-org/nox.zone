package zone.nox;

import dev.nipafx.ginevra.Ginevra;
import dev.nipafx.ginevra.outline.GeneralDocument;
import zone.nox.data.Post;
import zone.nox.templates.Landing;

import java.nio.file.Path;
import java.util.List;

public class Main {

	private static final Path POSTS = Path.of("nox.zone/src/main/resources/posts").toAbsolutePath();
	private static final Path RESOURCES = Path.of("nox.zone/src/main/resources/resources").toAbsolutePath();

	public static void main(String[] args) {
		var ginevra = Ginevra.initialize(args);
		var outliner = ginevra.newOutliner();

		var resources = outliner.sourceBinaryFiles("resources", RESOURCES);
		outliner.storeResource(resources);

		var content = outliner.sourceTextFiles("posts", POSTS);
		var markdown = outliner.transformMarkdown(content, Post.Md.class);
		var posts = outliner.transform(markdown, postMd -> List.of(new GeneralDocument<>(
				postMd.id().transform("parsed"),
				Post.fromMd(postMd.data()))));
		outliner.store(posts, "posts");

		outliner.generate(new Landing());

		outliner.build().run();
	}

}
