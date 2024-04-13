package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.HtmlDocumentData;
import dev.nipafx.ginevra.outline.Query;
import dev.nipafx.ginevra.outline.Query.RootQuery;
import dev.nipafx.ginevra.outline.Resources;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.Target;
import zone.nox.data.Post;
import zone.nox.data.Root;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;
import static java.util.Comparator.comparing;
import static zone.nox.components.Components.header;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.post;

public class Landing implements Template<Root>, CssStyled<Landing.Style> {

	public record Style(Classes header, Classes posts, Classes post, Classes city, Css css) implements CssStyle { }

	private static final Style STYLE = Css.parse(Style.class, """
			.header {
				margin-top: 1em;
			}
			
			.posts {
				display: flex;
				flex-direction: column;
				align-items: center;
			}
			
			.post {
				margin: 1.5em 0 0;
				padding: 1.5em 0 0;
				border-top: 1px solid var(--yellow);
				width: 100%
			}
			
			.post:first-child {
				border-top: 3px double var(--yellow);
			}
			
			.post > *:last-child {
				margin-bottom: 0;
			}
			
			.city {
				margin: 2em 0 2em;
				padding: 1.5em 0 0;
				border-top: 3px double var(--yellow);
				width: 100%
			}
			""");

	private final Target target;

	public Landing(Target target) {
		this.target = target;
	}

	@Override
	public Query<Root> query() {
		return new RootQuery<>(Root.class);
	}

	@Override
	public HtmlDocumentData compose(Root root) {
		return new HtmlDocumentData(Path.of(""), composePage(root));
	}

	private Element composePage(Root root) {
		return layout
				.title("Radio Nox")
				.description("News from the Shadows of Neotropolis.")
				.children(
						div.classes(STYLE.header).children(
								header("News from the Shadows of Neotropolis.", LocalDateTime.now())),
						div.classes(STYLE.posts).children(root
								.posts().stream()
								.sorted(comparing(Post::date).reversed())
								.map(post -> div
										.classes(STYLE.post)
										.children(post(post)
												.level(2)
												.embedLocalVideo(target.embedLocalVideo())
												.embedYouTubeVideo(target.embedYouTubeVideo())))
								.toList()),
						img.classes(STYLE.city).src(Resources.include("city.webp"))
				);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
