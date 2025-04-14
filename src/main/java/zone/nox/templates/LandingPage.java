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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.span;
import static java.util.Comparator.comparing;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.postBlock;

public class LandingPage implements Template {

	public record Style(Classes posts, Classes year, Classes post, Css css) implements CssStyle { }

	@StyledWith
	public static final Style STYLE = Css.parse(Style.class, """
			.posts {
				display: flex;
				flex-direction: column;

				margin: calc(var(--gap) / 2) 0;
				gap: var(--gap);
			}

			.year {
				font-family: var(--alt-font), "sans-serif";
				font-size: var(--font-size-small);
				color: var(--yellow);
				text-align: center;
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
		var postsByYear = posts.stream().collect(Collectors.groupingBy(post -> post.date().getYear()));
		return layout
				.slug("")
				.title("Radio Nox")
				.description("News from the Shadows of Neotropolis.")
				.thumbnail(Optional.of("landing.jpg"))
				.content(div
						.classes(STYLE.posts)
						.children(postsByYear
								.entrySet().stream()
								.sorted(Entry.<Integer, List<Post>> comparingByKey().reversed())
								.flatMap(entry -> composeYearWithPosts(entry.getKey(), entry.getValue()))
								.toList())
				);
	}

	private Stream<Element> composeYearWithPosts(int year, List<Post> posts) {
		return Stream.concat(
				Stream.of(span.classes(STYLE.year).text("Y" + year)),
				posts.stream()
						.sorted(comparing(Post::index).reversed())
						.map(post -> div
								.classes(STYLE.post)
								.children(postBlock(post)))
		);
	}

}
