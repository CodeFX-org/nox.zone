package zone.nox;

import dev.nipafx.ginevra.Ginevra;
import dev.nipafx.ginevra.config.SiteConfiguration;
import dev.nipafx.ginevra.outline.Outline;
import dev.nipafx.ginevra.outline.Outliner;
import zone.nox.data.Post;
import zone.nox.templates.FourOhFour;
import zone.nox.templates.LandingPage;
import zone.nox.templates.PostPage;

import java.net.URI;
import java.nio.file.Path;

public class Site implements SiteConfiguration {

	private static final Path POSTS = Path.of("src/main/resources/posts").toAbsolutePath();
	private static final Path RESOURCES = Path.of("src/main/resources/resources").toAbsolutePath();
	private static final Path SOCIAL_LINKS = RESOURCES.resolve("social-icons");
	private static final Path VIDEOS = Path.of("src/main/resources/videos").toAbsolutePath();
	private static final Path IMAGES = Path.of("src/main/resources/images").toAbsolutePath();

	private final Config config;

	public Site(Config config) {
		this.config = config;
	}

	public static void main(String[] args) {
		Ginevra.build(Site.class, args);
	}

	@Override
	public URI url() {
		return URI.create("https://nox.zone");
	}

	@Override
	public Outline createOutline(Outliner outliner) {
		outliner
				.sourceBinaryFiles("resources", RESOURCES)
				.storeResource(res -> res.file().getFileName().toString());
		outliner
				.sourceBinaryFiles("social-icons", SOCIAL_LINKS)
				.storeResource(res -> res.file().getFileName().toString());
		outliner
				.sourceBinaryFiles("videos", VIDEOS)
				.storeResource(res -> res.file().getFileName().toString());
		outliner
				.sourceBinaryFiles("images", IMAGES)
				.storeResource(res -> "/" + IMAGES.getParent().relativize(res.file()));

		outliner
				.sourceTextFiles("posts", POSTS)
				.transformMarkdown(Post.Md.class)
				.transform("parse-post", Post::fromMd)
				.store("posts");

		outliner.generate(new LandingPage());
		outliner.generate(new PostPage(Target.from(config)));
		outliner.generate(new FourOhFour());
		outliner.generateStaticResources(Path.of(""), "favicon.ico");

		return outliner.build();
	}

}
