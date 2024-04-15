package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Id;
import dev.nipafx.ginevra.outline.Resources;

import static dev.nipafx.ginevra.html.HtmlElement.a;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;
import static dev.nipafx.ginevra.html.HtmlElement.p;
import static dev.nipafx.ginevra.html.JmlElement.text;

public record Header(Id id, Classes classes) implements CustomSingleElement, CssStyled<Header.Style> {

	public record Style(Classes container, Classes title, Classes logo, Classes byline, Css css) implements CssStyle { }
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
			
			.logo > img {
				width: 100%;
			}
			
			.byline {
				text-align: center;
				font-size: var(--font-size-large);
				color: var(--yellow);
				font-family: var(--alt-font), "sans-serif";
			}
			
			.byline > a:visited {
				color: var(--yellow);
			}
			""");

	public Header() {
		this(Id.none(), Classes.none());
	}

	@Override
	public Style style() {
		return STYLE;
	}

	@Override
	public Element composeSingle() {
		return div
				.id(id)
				.classes(classes.plus(STYLE.container))
				.children(
						a.classes(STYLE.logo).href("/").children(
								img.src(Resources.include("logo.webp"))),
						p.classes(STYLE.byline).children(
								text.text("News from the Shadows of "),
								a.href("https://www.neotropolis.com").text("Neotropolis")));
	}

	public Header id(Id id) {
		return new Header(id, classes);
	}

	public Header classes(Classes classes) {
		return new Header(id, classes);
	}

}
