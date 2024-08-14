package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.Query;
import dev.nipafx.ginevra.outline.Query.RootQuery;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.data.Post;
import zone.nox.data.Root;

import java.nio.file.Path;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static java.util.Comparator.comparing;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.postBlock;

public class LandingTemplate implements Template<Root>, CssStyled<LandingTemplate.Style> {

	public record Style(Classes posts, Classes post, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.posts {
				display: flex;
				flex-direction: column;

				margin: calc(var(--gap) / 2) 0;
				gap: var(--gap);
			}

			.post {
				width: 100%
			}
			""");

	@Override
	public Query<Root> query() {
		return new RootQuery<>(Root.class);
	}

	@Override
	public HtmlPage compose(Root root) {
		return new HtmlPage(Path.of(""), composePage(root));
	}

	private Element composePage(Root root) {
		return layout
				.title("Radio Nox")
				.description("News from the Shadows of Neotropolis.")
				.content(div
						.classes(STYLE.posts)
						.children(root
								.posts().stream()
								.sorted(comparing(Post::index).reversed())
								.map(post -> div
										.classes(STYLE.post)
										.children(postBlock(post)))
								.toList())
				);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
