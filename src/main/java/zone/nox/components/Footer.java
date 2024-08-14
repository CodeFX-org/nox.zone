package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Id;
import dev.nipafx.ginevra.outline.Resources;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;

public record Footer(Id id, Classes classes) implements CustomSingleElement, CssStyled<Footer.Style> {

	public record Style(Classes container, Classes city, Css css) implements CssStyle { }
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

			.city {
				width: 100%
			}
			""");

	public Footer() {
		this(Id.none(), Classes.none());
	}

	@Override
	public Element composeSingle() {
		return div
				.id(id)
				.classes(classes.plus(STYLE.container))
				.children(img.classes(STYLE.city).src(Resources.include("city.webp")));
	}

	public Footer id(Id id) {
		return new Footer(id, classes);
	}

	public Footer classes(Classes classes) {
		return new Footer(id, classes);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
