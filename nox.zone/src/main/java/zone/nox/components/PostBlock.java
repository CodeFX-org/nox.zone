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
import static zone.nox.components.Components.header;

public record PostBlock(Post post, int level, boolean embedLocalVideo, boolean embedYouTubeVideo) implements CustomElement, CssStyled<PostBlock.Style> {

	public record Style(Classes localVideo, Classes youTubeVideoContainer, Classes youTubeVideo, Css css) implements CssStyle { }
	private static final Style STYLE = Css.parse(Style.class, """
			.localVideo {
				margin: 1em 2em 0.5em;
				border: 1px dotted var(--yellow);
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
				margin: 1em 2em 0.5em;
				border: 1px dotted var(--yellow);
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
			""");

	public PostBlock(Post post) {
		this(post, 1, false, false);
	}

	@Override
	public List<Element> compose() {
		var children = new ArrayList<Element>();
		children.add(header(post, level));
		if (embedLocalVideo)
			children.add(embeddedLocalVideo());
		if (embedYouTubeVideo)
			children.add(embeddedYouTubeVideo());
		children.addAll(post.content());
		return children;
	}

	private Element embeddedLocalVideo() {
		return post
				.localVideo()
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

	public PostBlock level(int level) {
		return new PostBlock(post, level, embedLocalVideo, embedYouTubeVideo);
	}

	public PostBlock embedLocalVideo(boolean embedLocalVideo) {
		return new PostBlock(post, level, embedLocalVideo, embedYouTubeVideo);
	}

	public PostBlock embedYouTubeVideo(boolean embedYouTubeVideo) {
		return new PostBlock(post, level, embedLocalVideo, embedYouTubeVideo);
	}

	@Override
	public Style style() {
		return STYLE;
	}

}
