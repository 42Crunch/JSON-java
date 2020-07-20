package org.json;

public class JSONLocation {

    private final long line;
    private final long column;
    private final long startOffset;
    private final long endOffset;

    public JSONLocation(long line, long column, long startOffset, long endOffset) {
        this.line = line;
        this.column = column;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public long getLine() {
        return line;
    }

    public long getColumn() {
        return column;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    @Override
    public String toString() {
        return "<line " + line + ", column " + column + ", offset (" + startOffset + ", " + endOffset + ")>";
    }
}
