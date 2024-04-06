package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.HtmlDocumentData;
import dev.nipafx.ginevra.outline.Query;
import dev.nipafx.ginevra.outline.Query.RootQuery;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.data.Post;
import zone.nox.data.Root;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static java.util.Comparator.comparing;
import static zone.nox.components.Components.header;
import static zone.nox.components.Components.layout;

public class Landing implements Template<Root>, CssStyled<Landing.Style> {

	public record Style(Classes container, Classes post, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.container {
				display: flex;
				flex-direction: column;
				align-items: center;
			}
			
			.post {
				margin: 1em 0 0;
				padding: 1em 0 0;
				border-top: 1px solid yellow;
				width: 100%
			}
			""");

	@Override
	public Query<Root> query() {
		return new RootQuery<>(Root.class);
	}

	@Override
	public HtmlDocumentData compose(Root root) {
		return new HtmlDocumentData(Path.of(""), composePage(root));
	}

	private Element composePage(Root root) {
		var children = Stream.concat(
						Stream
								.of(header("Radio Nox", "News from the Shadows of Neotropolis.", LocalDateTime.now())),
						root
								.posts().stream()
								.sorted(comparing(Post::date).reversed())
								.map(this::composePost))
				.toList();
		return layout
				.title("Radio Nox")
				.description("News from the Shadows of Neotropolis.")
				.children(div.classes(STYLE.container).children(children));
	}

	private Element composePost(Post post) {
		var children = new ArrayList<Element>(List.of(header(post, 2)));
		children.addAll(post.content());
		return div.classes(STYLE.post).children(children);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
