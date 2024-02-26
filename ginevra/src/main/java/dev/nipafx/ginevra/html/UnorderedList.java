package dev.nipafx.ginevra.html;

import java.util.List;

public record UnorderedList(String id, Classes classes, List<ListItem> children) implements HtmlElement {

	public UnorderedList {
		children = List.copyOf(children);
	}

	public UnorderedList() {
		this(null, Classes.none(), List.of());
	}

	public UnorderedList id(String id) {
		return new UnorderedList(id, this.classes, this.children);
	}

	public UnorderedList classes(Classes classes) {
		return new UnorderedList(this.id, classes, this.children);
	}

	public UnorderedList children(List<ListItem> children) {
		return new UnorderedList(this.id, this.classes, children);
	}

	public UnorderedList children(ListItem... children) {
		return new UnorderedList(this.id, this.classes, List.of(children));
	}

}
