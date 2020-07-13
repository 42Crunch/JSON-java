package org.json;


public class JSONTrack {

    private long line = 0;
    private long column = 0;
    private long startOffset = 0;
    private long endOffset = 0;

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public long getColumn() {
        return column;
    }

    public void setColumn(long column) {
        this.column = column;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    @Deprecated
    public long getOffset() {
        return getEndOffset();
    }

    @Deprecated
    public void setOffset(long offset) {
        setEndOffset(offset);
    }
}
