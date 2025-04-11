package zone.nox.templates;

import dev.nipafx.ginevra.outline.Compose;
import dev.nipafx.ginevra.outline.HtmlPage;
import dev.nipafx.ginevra.outline.Slug;
import dev.nipafx.ginevra.outline.Template;

import static zone.nox.components.Components.layout;
import static zone.nox.components.Components.pageHeader;

public class FourOhFour implements Template {

	@Compose
	public HtmlPage compose() {
		return new HtmlPage(
				new Slug("404", Slug.SlugStyle.FILE),
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
