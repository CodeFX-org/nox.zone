package zone.nox.components;

import dev.nipafx.ginevra.css.Css;
import dev.nipafx.ginevra.css.CssStyle;
import dev.nipafx.ginevra.css.CssStyled;
import dev.nipafx.ginevra.html.Classes;
import dev.nipafx.ginevra.html.CustomElement;
import dev.nipafx.ginevra.html.Element;
import dev.nipafx.ginevra.html.Video.Preload;
import dev.nipafx.ginevra.outline.Resources;
import zone.nox.data.Post;

import java.util.ArrayList;
import java.util.List;

import static dev.nipafx.ginevra.html.HtmlElement.div;
import static dev.nipafx.ginevra.html.HtmlElement.video;
import static dev.nipafx.ginevra.html.JmlElement.html;
import static dev.nipafx.ginevra.html.JmlElement.nothing;
import static zone.nox.components.Components.postHeader;

public record PostContent(Post post, boolean embedLocalVideo, boolean embedYouTubeVideo) implements CustomElement, CssStyled<PostContent.Style> {

	public record Style(
			Classes header, Classes content,
			Classes localVideo, Classes youTubeVideoContainer, Classes youTubeVideo, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.header {
				margin-bottom: 1em;
			}

			.localVideo {
				margin: 0 2em 1em;
				border: 1px solid var(--yellow);
			}

			.localVideo > video {
				display: block;
				width: 100% !important;
				height: auto !important;
			}

			/* the second container (below) can't have margins
				because they screw up the 16:9 padding-top computation,
				so we have *another* container, just for the margins */
			.youTubeVideoContainer {
				margin: 0 2em 1em;
				border: 1px solid var(--yellow);
			}

			.youTubeVideo {
				position: relative;
				overflow: hidden;
				/* 16:9 portrait aspect ratio */
				padding-top: 177.78%;
			}

			.youTubeVideo > iframe {
				display: block;
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				border: 0;
			}

			.content > *:first-child {
				margin-top: 0;
			}
			.content > *:last-child {
				margin-bottom: 0;
			}
			""");

	public PostContent(Post post) {
		this(post, false, false);
	}

	@Override
	public List<Element> compose() {
		return List.of(
				div.classes(STYLE.header).children(postHeader(post)),
				embeddedLocalVideo(),
				embeddedYouTubeVideo(),
				div.classes(STYLE.content).children(post.content()));
	}

	private Element embeddedLocalVideo() {
		if (!embedLocalVideo)
			return nothing;

		return post
				.localFile()
				.<Element> map(local -> div
						.classes(STYLE.localVideo())
						.children(video
								.src(Resources.include(local + ".mp4"))
								.poster(Resources.include(local + ".jpg"))
								.height(711)
								.width(400)
								.preload(Preload.NONE)
								.controls(true)))
				.orElse(nothing);
	}

	private Element embeddedYouTubeVideo() {
		if (!embedYouTubeVideo)
			return nothing;

		return post
				.youTubeId()
				.<Element>map(id -> div
						.classes(STYLE.youTubeVideoContainer())
						.children(div
								.classes(STYLE.youTubeVideo())
								.children(html.literal("""
										<iframe src="https://www.youtube.com/embed/%s" frameborder="0" allowfullscreen allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture">
										</iframe>
										""".formatted(id)))))
				.orElse(nothing);
	}

	public PostContent embedLocalVideo(boolean embedLocalVideo) {
		return new PostContent(post, embedLocalVideo, embedYouTubeVideo);
	}

	public PostContent embedYouTubeVideo(boolean embedYouTubeVideo) {
		return new PostContent(post, embedLocalVideo, embedYouTubeVideo);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
