package com.efimchick.ifmo.io.filetree;

public class TreeResult {
    private final String tree;
    private final long size;

    public TreeResult(String tree, long size) {
        this.tree = tree;
        this.size = size;
    }
    public String getTree() {
        return tree;
    }

    public long getSize() {
        return size;
    }
}