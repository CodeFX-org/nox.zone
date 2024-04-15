package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import zone.nox.data.Post;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.h1;
import static dev.nipafx.ginevra.html.HtmlElement.p;
import static zone.nox.components.Components.format;

public record PostHeader(Post post) implements CustomSingleElement, CssStyled<PostHeader.Style> {

	public record Style(Classes container, Classes title, Classes date, Classes summary, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.container {
				display: flex;
				flex-direction: column;
				align-items: center;
			
				margin-top: calc(var(--gap) / 2);
				gap: calc(var(--gap) / 2) 0;
			}
			
			.container > * {
				margin: 0;
			}
			
			.title {
				text-align: center;
			}
			
			.date {
				text-align: center;
				font-size: var(--font-size-small);
				color: var(--gray);
			}
			
			.summary {
				font-style: italic;
			}
			""");

	@Override
	public Style style() {
		return STYLE;
	}

	@Override
	public Element composeSingle() {
		return div
				.classes(STYLE.container)
				.children(
						h1.classes(STYLE.title).text(post().title()),
						p.classes(STYLE.date).text("#%03d / %s".formatted(post.index(), format(post.date()))),
						p.classes(STYLE.summary).text(post().summary() + period(post().summary()))
				);
	}

	private static String period(String text) {
		return switch (text.charAt(text.length() - 1)) {
			case '.', '!', '?', ':' -> "";
			default -> ".";
		};
	}

}
