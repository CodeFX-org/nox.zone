package zone.nox;

import dev.nipafx.ginevra.Ginevra;
import dev.nipafx.ginevra.config.SiteConfiguration;
import dev.nipafx.ginevra.outline.Outline;
import dev.nipafx.ginevra.outline.Outliner;
import zone.nox.data.Post;
import zone.nox.templates.FourOhFour;
import zone.nox.templates.LandingTemplate;
import zone.nox.templates.PostTemplate;

import java.nio.file.Path;

public class Main implements SiteConfiguration {

	private static final Path POSTS = Path.of("src/main/resources/posts").toAbsolutePath();
	private static final Path RESOURCES = Path.of("src/main/resources/resources").toAbsolutePath();
	private static final Path SOCIAL_LINKS = RESOURCES.resolve("social-icons");
	private static final Path VIDEOS = Path.of("src/main/resources/videos").toAbsolutePath();

	private final Config config;

	public Main(Config config) {
		this.config = config;
	}

	public static void main(String[] args) {
		Ginevra.build(Main.class, args);
	}

	@Override
	public Outline createOutline(Outliner outliner) {
		outliner
				.sourceBinaryFiles("resources", RESOURCES)
				.storeResource();
		outliner
				.sourceBinaryFiles("social-icons", SOCIAL_LINKS)
				.storeResource();
		outliner
				.sourceBinaryFiles("videos", VIDEOS)
				.storeResource();

		outliner
				.sourceTextFiles("posts", POSTS)
				.transformMarkdown(Post.Md.class)
				.transform("parse-post", Post::fromMd)
				.store("posts");

		outliner.generate(new LandingTemplate());
		outliner.generate(new PostTemplate(Target.from(config)));
		outliner.generate(new FourOhFour());
		outliner.generateStaticResources(Path.of(""), "favicon.ico");

		return outliner.build();
	}

}
