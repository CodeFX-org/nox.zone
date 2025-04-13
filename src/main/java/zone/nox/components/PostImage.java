package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.StyledWith;
import dev.nipafx.ginevra.html.*;
import dev.nipafx.ginevra.outline.Resources;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.img;

public record PostImage(String src, Ratio ratio, String alt) implements Component {

	public record Style(Classes outer, Classes inner, Classes image,
						Classes r_16_9, Classes r_3_2, Classes r_4_3, Classes r_1_1, Classes r_3_4, Classes r_2_3, Classes r_9_16,
						Css css) implements CssStyle { }

	@StyledWith
	public static final Style STYLE = Css.parse(Style.class, """
			.outer {
				padding: 0 1em;
			}

			.inner {
				position: relative;
				overflow: hidden;

				border: 1px solid var(--yellow);
			}

			.r_16_9 {
				padding-top: 56.25%;
			}

			.r_3_2 {
				padding-top: 66.67%;
			}

			.r_4_3 {
				padding-top: 75%;
			}

			.r_1_1 {
				padding-top: 100%;
			}

			.r_3_4 {
				padding-top: 133.33%;
			}

			.r_2_3 {
				padding-top: 150%;
			}

			.r_9_16 {
				padding-top: 177.78%;
			}

			.image {
				display: block;
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
			}
			
			.youTubeVideo {
				position: relative;
				overflow: hidden;
				/* 16:9 portrait aspect ratio */
				padding-top: 177.78%;
			}

			.youTubeVideo > image {
				display: block;
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				border: 0;
			}
			""");

	public PostImage(Image image) {
		var src = image.src().asHtmlValue();
		var settings = image.alt().split(" \\| ");
		var ratio = Ratio.parse(settings[0]);
		var alt = settings.length > 1 ? settings[1] : "";
		this(src, ratio, alt);
	}

	@Override
	public Element compose() {
		return div
				.classes(STYLE.outer)
				.children(div
						.classes(STYLE.inner.plus(ratio.toCssClass()))
						.children(img
								.classes(STYLE.image)
								.src(Resources.include(src))
								.alt(alt)));
	}

	private enum Ratio {

		_16_9, _3_2, _4_3, _1_1, _3_4, _2_3, _9_16;

		static Ratio parse(String value) {
			return switch (value) {
				case "16:9" -> _16_9;
				case "3:2" -> _3_2;
				case "4:3" -> _4_3;
				case "1:1" -> _1_1;
				case "3:4" -> _3_4;
				case "2:3" -> _2_3;
				case "9:16" -> _9_16;
				default -> throw new IllegalArgumentException("Unknown ratio: " + value);
			};
		}

		Classes toCssClass() {
			return switch (this) {
				case _16_9 -> STYLE.r_16_9;
				case _3_2 -> STYLE.r_3_2;
				case _4_3 -> STYLE.r_4_3;
				case _1_1 -> STYLE.r_1_1;
				case _3_4 -> STYLE.r_3_4;
				case _2_3 -> STYLE.r_2_3;
				case _9_16 -> STYLE.r_9_16;
			};
		}

	}

}
