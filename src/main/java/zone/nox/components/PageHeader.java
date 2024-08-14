package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomSingleElement;
import dev.nipafx.ginevra.html.Element;
import zone.nox.data.Post;

import java.util.Optional;

import static dev.nipafx.ginevra.html.GmlElement.nothing;
import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.h1;
import static dev.nipafx.ginevra.html.HtmlElement.p;
import static zone.nox.components.Components.format;

public record PageHeader(String title, String summary, Optional<String> dateLine) implements CustomSingleElement, CssStyled<PageHeader.Style> {

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
	public Element composeSingle() {
		return div
				.classes(STYLE.container)
				.children(
						h1.classes(STYLE.title).text(title),
						dateLine.<Element> map(p.classes(STYLE.date)::text).orElse(nothing),
						p.classes(STYLE.summary).text(summary + period(summary))
				);
	}

	private static String period(String text) {
		return switch (text.charAt(text.length() - 1)) {
			case '.', '!', '?', ':' -> "";
			default -> ".";
		};
	}

	public PageHeader summary(String summary) {
		return new PageHeader(title, summary, dateLine);
	}

	public PageHeader title(String title) {
		return new PageHeader(title, summary, dateLine);
	}

	public PageHeader dateLine(String dateLine) {
		return new PageHeader(title, summary, Optional.of(dateLine));
	}

	public PageHeader post(Post post) {
		var dateLine = "#%03d / %s".formatted(post.index(), format(post.date()));
		return new PageHeader(post.title(), post.summary(), Optional.of(dateLine));
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
