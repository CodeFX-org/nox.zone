package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import zone.nox.data.Post;

import static dev.nipafx.ginevra.html.HtmlElement.a;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.span;
import static zone.nox.components.Components.format;

public record PostBlock(Post post) implements CustomSingleElement, CssStyled<PostBlock.Style> {

	public record Style(Classes block, Classes index, Classes title, Classes date, Classes more, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.block {
				display: grid;
				grid-template-columns: minmax(5em,auto) 1fr auto;
				grid-template-areas:
					"index title title"
					".     date  more";
			}

			.index {
				grid-area: index;
				margin-right: 0.5em;
				font-family: var(--alt-font), "sans-serif";
				font-size: var(--font-size-large);
			}

			.title {
				grid-area: title;
				font-size: var(--font-size-large);
				font-weight: bold;
			}

			a.title {
				color: white;
			}
			a.title:hover {
				color: white;
				text-decoration: none;
			}
			a.title:visited {
				color: white;
			}

			.date {
				grid-area: date;
				font-size: var(--font-size-small);
				color: var(--gray);
			}

			.more {
				grid-area: more;
				font-family: var(--alt-font), "sans-serif";
				font-size: var(--font-size-small);
				text-align: end;
			}
			""");

	@Override
	public Element composeSingle() {
		return div.classes(STYLE.block).children(
				a.classes(STYLE.index).href(post.slug().toString()).text("#%03d".formatted(post.index())),
				a.classes(STYLE.title).href(post.slug().toString()).text(post.title()),
				span.classes(STYLE.date).text(format(post.date())),
				a.classes(STYLE.more).href(post.slug().toString()).text("More >>")
		);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
