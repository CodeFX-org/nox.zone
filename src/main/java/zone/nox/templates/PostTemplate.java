package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.Query;
import dev.nipafx.ginevra.outline.Query.CollectionQuery;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.Target;
import zone.nox.data.Post;

import static dev.nipafx.ginevra.html.HtmlElement.a;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.postContent;

public record PostTemplate(Target target) implements Template<Post>, CssStyled<PostTemplate.Style> {

	public record Style(Classes back, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.back {
				margin-top: var(--gap);
				font-family: var(--alt-font), "sans-serif";
			}

			.back:visited {
				color: var(--yellow);
			}
			""");

	@Override
	public Query<Post> query() {
		return new CollectionQuery<>("posts", Post.class);
	}

	@Override
	public HtmlPage compose(Post post) {
		return new HtmlPage(post.slug(), composePage(post));
	}

	private Element composePage(Post post) {
		return layout
				.title(post.title())
				.description(post.summary())
				.content(
						postContent(post)
								.embedLocalVideo(target.embedLocalVideo())
								.embedYouTubeVideo(target.embedYouTubeVideo()),
						a.classes(STYLE.back).href("/").text("<< Back"));
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
