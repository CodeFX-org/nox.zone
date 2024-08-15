package zone.nox.templates;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.HtmlPage.SlugStyle;
import dev.nipafx.ginevra.outline.SingleTemplate;

import java.nio.file.Path;

import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.pageHeader;

public class FourOhFour implements SingleTemplate {

	public record Style(Classes red, Css css) implements CssStyle { }

	@Override
	public HtmlPage composeSingle() {
		return new HtmlPage(
				Path.of("404"),
				SlugStyle.FILE,
				layout
						.title("404")
						.description("Page not found")
						.content(
								pageHeader
										.title("404 - Page not Found")
										.summary("Sorry, this page doesn't exist... is what they want you to believe"))
		);
	}

}
