package zone.nox;

import dev.nipafx.ginevra.Ginevra;
import dev.nipafx.ginevra.outline.GeneralDocument;
import dev.nipafx.ginevra.outline.Outliner.StepKey;
import dev.nipafx.ginevra.outline.TextFileData;
import zone.nox.data.Post;
import zone.nox.templates.Landing;

import java.nio.file.Path;
import java.util.List;

public class Main {

	private static final Path POSTS = Path.of("nox.zone/src/main/resources/posts").toAbsolutePath();

	public static void main(String[] args) {
		var ginevra = Ginevra.initialize(args);
		var outliner = ginevra.newOutliner();

		StepKey<TextFileData> content = outliner.sourceTextFiles("posts", POSTS);
		StepKey<Post.Md> markdown = outliner.transformMarkdown(content, Post.Md.class);
		StepKey<Post> post = outliner.transform(markdown, postMd -> List.of(new GeneralDocument<>(
				postMd.id().transform("parsed"),
				Post.fromMd(postMd.data()))));
		outliner.store(post, "posts");

		outliner.generate(new Landing());

		outliner.build().run();
	}

}
