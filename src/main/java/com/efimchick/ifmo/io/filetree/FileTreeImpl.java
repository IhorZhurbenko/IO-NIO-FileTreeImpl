package com.efimchick.ifmo.io.filetree;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileTreeImpl implements FileTree {
    @Override
    public Optional<String> tree(Path path) {
        if (path == null || !path.toFile().exists()) {
            return Optional.empty();
        }
        File file = path.toFile();
        if (file.isFile()) {
            return Optional.of(printFileNameSize(file));
        }
        return Optional.of(buildTree(file, "").getTree());
    }

    private String printFileNameSize(File file) {
        return file.getName() + " " + file.length() + " bytes";
    }

    private TreeResult buildTree(File directory, String indent) {
        List<File> files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        files.sort((File a, File b) -> Boolean.compare(a.isFile(), b.isFile()));
        long size = 0;
        StringBuilder treeSB = new StringBuilder();
        for (File file : files) {
            StringBuilder indentSB = new StringBuilder(indent);
            boolean isLast = file.equals(files.get(files.size() - 1));
            indentSB.append(isLast ? "   " : "│  ");
            treeSB.append("\n").append(indent).append(isLast ? "└─ " : "├─ ");
            if (file.isFile()) {
                size += file.length();
                treeSB.append(printFileNameSize(file));
            }
            if (file.isDirectory()) {
                TreeResult recursionTree = buildTree(file, indentSB.toString());
                size += recursionTree.getSize();
                treeSB.append(recursionTree.getTree());
            }
        }
        return new TreeResult(directory.getName() + " " + size + " bytes" + treeSB, size);
    }
}