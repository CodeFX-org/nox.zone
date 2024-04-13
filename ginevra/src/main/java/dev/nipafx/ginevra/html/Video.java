package dev.nipafx.ginevra.html;

import java.util.List;

public record Video(
		Id id, Classes classes, Src src, Integer height, Integer width, Src poster, Preload preload,
		Boolean autoplay, Boolean loop, Boolean muted, Boolean playinline,
		Boolean controls, Boolean disablepictureinpicture, Boolean disableremoteplayback,
		List<? extends Element> children) implements HtmlElement {

	public Video() {
		this(
				Id.none(), Classes.none(), Src.none(), null, null, null, null,
				null, null, null, null, null, null, null, List.of());
	}

	public Video id(Id id) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video classes(Classes classes) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video src(Src src) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video height(Integer height) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video width(Integer width) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video poster(Src poster) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video preload(Preload preload) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video autoplay(Boolean autoplay) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video loop(Boolean loop) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video muted(Boolean muted) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video playinline(Boolean playinline) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video controls(Boolean controls) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video disablepictureinpicture(Boolean disablepictureinpicture) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video disableremoteplayback(Boolean disableremoteplayback) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video children(List<? extends Element> children) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, children);
	}

	public Video children(Element... children) {
		return new Video(
				id, classes, src, height, width, poster, preload,
				autoplay, loop, muted, playinline, controls, disablepictureinpicture, disableremoteplayback, List.of(children));
	}

	public enum Preload {
		NONE, METADATA, AUTO;

		@Override
		public String toString() {
			return switch (this) {
				case NONE -> "none";
				case METADATA -> "metadata";
				case AUTO -> "auto";
			};
		}

	}

}
