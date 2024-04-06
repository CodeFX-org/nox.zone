package zone.nox.data;

import dev.nipafx.ginevra.outline.Document;

import java.util.List;

public record Root(List<Post> posts) implements Document.Data { }
