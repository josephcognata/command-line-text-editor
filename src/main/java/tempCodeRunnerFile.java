    // public void undo() {
    //     pushUndo();
    //     String undoFileName = undoArr.remove(index);
    //     redoArr.push(undoFileName);
    //     copyFileUsingStream(new File(undoFileName), new File(this.fileName));

    // }

    // public void redo() {
    //     String redoFileName = redoArr.pop();
    //     undoArr.push(redoFileName);
    //     copyFileUsingStream(new File(redoFileName), new File(this.fileName));
    // }