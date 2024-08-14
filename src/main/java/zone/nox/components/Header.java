package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Id;
import dev.nipafx.ginevra.outline.Resources;

import static dev.nipafx.ginevra.html.GmlElement.text;
import static dev.nipafx.ginevra.html.HtmlElement.a;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;
import static dev.nipafx.ginevra.html.HtmlElement.p;

public record Header(Id id, Classes classes) implements CustomSingleElement, CssStyled<Header.Style> {

	public record Style(Classes container, Classes title, Classes logo, Classes byline, Classes social, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.container {
				display: flex;
				flex-direction: column;
				align-items: center;
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
				margin-top: 0.25em;
				text-align: center;
				font-size: var(--font-size-large);
				color: var(--yellow);
				font-family: var(--alt-font), "sans-serif";
			}

			.byline > a:visited {
				color: var(--yellow);
			}

			.social {
				margin-top: 0.75em;
				display: flex;
				flex-direction: row;
			}

			.social a {
				display: block;
				margin: 0 5px;
				padding: 5px;
				border: 1px solid transparent;
				border-radius: 5px;
			}

			.social a:hover {
				border-color: var(--yellow);
			}

			.social img {
				display: block;
				width: calc(var(--font-size-large));
			}
			""");

	public Header() {
		this(Id.none(), Classes.none());
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
								a.href("https://www.neotropolis.com").text("Neotropolis")),
						div.classes(STYLE.social).children(
								a.href("https://www.tiktok.com/@radionox").children(img.src(Resources.include("tiktok.png"))),
								a.href("https://www.youtube.com/@radiorox").children(img.src(Resources.include("youtube.png"))),
								a.href("https://www.facebook.com/profile.php?id=61556212299318").children(img.src(Resources.include("facebook.png"))),
								a.href("https://twitter.com/RadioNoxx").children(img.src(Resources.include("twitter.png"))),
								a.href("https://mastodon.art/@radionox").children(img.src(Resources.include("mastodon.png"))),
								a.href("https://bsky.app/profile/radionox.bsky.social").children(img.src(Resources.include("bluesky.png")))));
	}

	public Header id(Id id) {
		return new Header(id, classes);
	}

	public Header classes(Classes classes) {
		return new Header(id, classes);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
