package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Heading;

import java.time.LocalDateTime;
import java.util.List;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.p;

public record Header(String title, String text, LocalDateTime dateTime, int level) implements CustomSingleElement, CssStyled<Header.Style> {

	public record Style(Classes container, Classes title, Classes description, Classes date, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.container {
				display: flex;
				flex-direction: column;
				align-items: center;
				gap: 0.5em;
				margin: 1em 0 0.5em;
			}
			
			.container > * {
				margin: 0;
			}
			
			.date {
				color: #ccc;
			}
			
			.description {
				font-size: 1.2em;
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
				.children(List.of(
						new Heading(level).classes(STYLE.title).text(title),
						p.classes(STYLE.date).text(Components.format(dateTime)),
						p.classes(STYLE.description).text(text)));
	}

}
