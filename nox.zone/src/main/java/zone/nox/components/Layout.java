package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static dev.nipafx.ginevra.html.HtmlElement.body;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.document;
import static dev.nipafx.ginevra.html.HtmlElement.head;
import static dev.nipafx.ginevra.html.HtmlElement.meta;

public record Layout(String title, String description, List<? extends Element> children) implements CustomSingleElement, CssStyled<Layout.Style> {

	public record Style(Classes layout, Classes content, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			body {
				margin: 0;
				min-width: 320px;
			
				background-color: #222;
				color: white;
			
				font-size: 18px;
			}
			
			h1 {
				font-size: 3em;
				font-weight: bold;
			}
			
			h2 {
				font-size: 2.2em;
				font-weight: bold;
			}
			
			h3 {
				font-size: 1.8em;
				font-weight: bold;
			}
			
			h4 {
				font-size: 1.5em;
				font-weight: bold;
			}
			
			a {
				color: white;
			}
			
			a:visited {
				color: #aaa;
			}
			
			.layout {
				display: grid;
				grid-template-columns: 10px 1fr 10px;
				grid-template-areas:
					". content .";
			}
			
			.content {
				grid-area: content;
			}
			
			@media all and (min-width: 620px) {
				.layout {
					grid-template-columns: 1fr 600px 1fr;
				}
			}
			""");

	@Override
	public Style style() {
		return STYLE;
	}

	@Override
	public Element composeSingle() {
		return document
				.language(Locale.US)
				.head(head
						.charset(StandardCharsets.UTF_8)
						.title(title)
						.children(
								meta.name("viewport").content("width=device-width, initial-scale=1"),
								meta.name("description").content(description)))
				.body(body.classes(STYLE.layout).children(
						div.classes(STYLE.content).children(children)
				));
	}

	public Layout title(String title) {
		return new Layout(title, this.description, this.children);
	}

	public Layout description(String description) {
		return new Layout(this.title, description, this.children);
	}

	public Layout children(List<? extends Element> children) {
		return new Layout(this.title, this.description, children);
	}

	public Layout children(Element... children) {
		return new Layout(this.title, this.description, List.of(children));
	}

}
