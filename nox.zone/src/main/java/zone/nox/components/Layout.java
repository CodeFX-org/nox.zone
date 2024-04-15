package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.outline.Resources;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static dev.nipafx.ginevra.html.HtmlElement.body;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.document;
import static dev.nipafx.ginevra.html.HtmlElement.head;
import static dev.nipafx.ginevra.html.HtmlElement.meta;
import static zone.nox.components.Components.footer;
import static zone.nox.components.Components.header;

public record Layout(String title, String description, List<? extends Element> content) implements CustomSingleElement, CssStyled<Layout.Style> {

	public record Style(Classes layout, Classes page, Classes header, Classes content, Classes footer, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			body {
				margin: 0;
				min-width: 320px;

				font-family: "sans-serif";
				--alt-font: "space-punk";
				font-size: 18px;
				--font-size-large: 24px;
				--font-size-small: 16px;
				--gap: 1.5em;

				--yellow: #fcee0a;
				--gray: #999;
				color: white;
				background: url(\"""", Resources.include("hex.webp"), """
			") #171415;
			}

			@font-face {
				font-family: "space-punk";
				font-style: normal;
				font-weight: normal;
				src: url(\"""", Resources.include("space-punk.regular.ttf"), """
			") format("truetype");
				font-display: swap;
			}

			h1 {
				font-size: calc(1.25 * var(--font-size-large));
				font-weight: bold;
			}

			h2 {
				font-size: calc(1 * var(--font-size-large));
				font-weight: bold;
			}

			h3 {
				font-size: calc(0.8 * var(--font-size-large));
				font-weight: bold;
			}

			h4 {
				font-size: calc(0.7 * var(--font-size-large));
				font-weight: bold;
			}

			a {
				color: var(--yellow);
				text-decoration: none;
			}

			a:hover {
				text-decoration: underline;
			}

			a:visited {
				color: var(--gray);
				text-decoration: none;
			}

			.layout {
				display: grid;
				grid-template-columns: 10px 1fr 10px;
				grid-template-areas:
					". content .";
			}

			.page {
				grid-area: content;

				display: flex;
				flex-direction: column;
			}

			.header {
				margin: var(--gap) 0 var(--gap);
				padding: 0 0 var(--gap);
				border-bottom: 3px double var(--yellow);
			}

			.content {
				display: flex;
				flex-direction: column;
			}

			.footer {
				margin: var(--gap) 0 var(--gap);
				padding: var(--gap) 0 0;
				border-top: 3px double var(--yellow);
			}

			@media all and (min-width: 620px) {
				body {
					--font-size-large: 32px;
					--gap: 2em;
				}

			.layout {
					grid-template-columns: 1fr 600px 1fr;
				}
			}
			""");

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
				.body(body
						.classes(STYLE.layout)
						.children(div
								.classes(STYLE.page)
								.children(
										header.classes(STYLE.header),
										div.classes(STYLE.content).children(content),
										footer.classes(STYLE.footer))));
	}

	public Layout title(String title) {
		return new Layout(title, this.description, this.content);
	}

	public Layout description(String description) {
		return new Layout(this.title, description, this.content);
	}

	public Layout content(List<? extends Element> children) {
		return new Layout(this.title, this.description, children);
	}

	public Layout content(Element... children) {
		return new Layout(this.title, this.description, List.of(children));
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
