package com.demo.filesystem.model;

import com.demo.filesystem.kernel.DirectoryEntry;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class myTreeItem extends TreeItem<String> {
    private boolean isInitialized = false;                          // child是否已加载
    private final DirectoryEntry directoryEntry;

    public myTreeItem(DirectoryEntry directoryEntry){
        super(directoryEntry.getName());
        System.out.println(directoryEntry.getName());
        this.directoryEntry = directoryEntry;
    }

    @Override
    public ObservableList<TreeItem<String>> getChildren() {         // 展开时发生事件
        ObservableList<TreeItem<String>> children = super.getChildren();
        if (!isInitialized && isExpanded()) {                       // 未加载child
            System.out.println("getChildren");
            isInitialized = true;
            assert(directoryEntry.isDirectory());
            for (DirectoryEntry de : directoryEntry.listDirectoryEntries())     // 根据子目录项生成child
                if (de.isDirectory()) children.add(new myTreeItem(de));
        }
        return children;
    }

    // 判断是否可以点击展开
    @Override
    public boolean isLeaf() {
        return !directoryEntry.isDirectory();
    }

    public void addChildren(DirectoryEntry entry){
        this.directoryEntry.addDirectoryEntry(entry);
    }

    public DirectoryEntry getDirectoryEntry(){
        return this.directoryEntry;
    }
}
