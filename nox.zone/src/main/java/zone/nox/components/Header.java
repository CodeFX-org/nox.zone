package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Heading;
import dev.nipafx.ginevra.outline.Resources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;
import static dev.nipafx.ginevra.html.HtmlElement.p;

public record Header(Optional<String> title, String text, LocalDateTime dateTime, int level) implements CustomSingleElement, CssStyled<Header.Style> {

	public record Style(Classes container, Classes title, Classes logo, Classes description, Classes date, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.container {
				display: flex;
				flex-direction: column;
				align-items: center;
				gap: 0.75em;
			}
			
			.container > * {
				margin: 0;
			}
			
			.logo {
				width: 60%;
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
						p.classes(STYLE.date).text(Components.format(dateTime)),
						title
								.<Element> map(t -> new Heading(level).classes(STYLE.title).text(t))
								.orElse(img.classes(STYLE.logo).src(Resources.include("logo.webp"))),
						p.classes(STYLE.description).text(text)));
	}

}
