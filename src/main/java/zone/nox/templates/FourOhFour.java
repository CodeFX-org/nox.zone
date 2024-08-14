package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.Query;
import dev.nipafx.ginevra.outline.Query.RootQuery;
import dev.nipafx.ginevra.outline.Template;
import zone.nox.data.Root;

import java.nio.file.Path;

import static dev.nipafx.ginevra.html.HtmlElement.h1;
import static dev.nipafx.ginevra.html.HtmlElement.p;
import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.pageHeader;

public class FourOhFour implements Template<Root>, CssStyled<FourOhFour.Style> {

	public record Style(Css css) implements CssStyle { }

	private static final Style STYLE = Css.parse(Style.class, """
			
			""");

	@Override
	public Style style() {
		return STYLE;
	}

	@Override
	public Query<Root> query() {
		return new RootQuery<>(Root.class);
	}

	@Override
	public HtmlPage compose(Root root) {
		return new HtmlPage(
				Path.of("404"),
				layout
						.title("404")
						.description("Page not found")
						.content(
								pageHeader
										.title("404 - Page not Found")
										.summary("Sorry, this page doesn't exist. (Is what they want you to believe.)"))
		);
	}

}
