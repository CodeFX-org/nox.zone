package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.StyledWith;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.Compose;
import dev.nipafx.ginevra.outline.ForAllIn;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.data.Post;

import java.util.List;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static java.util.Comparator.comparing;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.postBlock;

public class LandingPage implements Template {

	public record Style(Classes posts, Classes post, Css css) implements CssStyle { }

	@StyledWith
	public static final Style STYLE = Css.parse(Style.class, """
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

	@Compose
	public HtmlPage compose(@ForAllIn("posts") List<Post> posts) {
		return new HtmlPage("/", composePage(posts));
	}

	private Element composePage(List<Post> posts) {
		return layout
				.title("Radio Nox")
				.description("News from the Shadows of Neotropolis.")
				.content(div
						.classes(STYLE.posts)
						.children(posts.stream()
								.sorted(comparing(Post::index).reversed())
								.map(post -> div
										.classes(STYLE.post)
										.children(postBlock(post)))
								.toList())
				);
	}

}
