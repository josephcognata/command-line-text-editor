import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.io.FileInputStream;

public class Editor {

    String fileName;
    EditorState editorState;
    Stack<EditorState> undoArr = new Stack<EditorState>();
    Stack<EditorState> redo = new Stack<EditorState>();

    public Editor(String fileName) {
        this.editorState = new EditorState();
        this.fileName = fileName;
        Scanner scanner = null;
        FileInputStream fInput = null;
        int cursor = 0;

        try {
            fInput = new FileInputStream(fileName);
            scanner = new Scanner(fInput);
            String text = "";
            while (scanner.hasNext()) {
                text += scanner.nextLine();
                text += "\n";
                this.editorState = new EditorState(text, cursor);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        scanner.close();

    }

    public void moveCursorForward() {
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        this.editorState.moveCursorForward();
    }

    public void moveCursorBackward() {
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        this.editorState.moveCursorBackward();

    }

    public void replace(int numChar, String replaceText) {
        int cursor = this.editorState.getCursorPosition();
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        delete(numChar);
        insert(replaceText);
        undoArr.pop();
        this.editorState.setCursorPosition(cursor);
        System.out.print(this.toString());

    }

    public void delete(int numChar) {
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        int cursor = this.editorState.getCursorPosition();
        String text = this.editorState.getText();
        int length = text.length();
        String textAfterDeletion = "";

        for (int i = 0; i < length; i++) {
            if (i == cursor) {
                for (int j = 0; j < numChar; j++) {
                    i++;
                }
            }
            textAfterDeletion += text.charAt(i);
        }

        this.editorState = new EditorState(textAfterDeletion, cursor);
        this.editorState.setCursorPosition(cursor);

    }

    public void search(String key) {
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        String text = this.editorState.getText();
        String textAfterCursor = "";

        if (text.contains(key)) {
            textAfterCursor = this.editorState.getText().substring(this.editorState.getCursorPosition());
            this.editorState.setCursorPosition(textAfterCursor.indexOf(key) + this.editorState.getCursorPosition());

        } else {
            this.editorState.setCursorPosition(text.length());
        }

    }

    public void insert(String textToInsert) {
        this.undoArr.push(this.editorState.clone());
        redo.clear();
        int cursor = this.editorState.getCursorPosition();
        String text = this.editorState.getText();
        int length = text.length();
        String textWithInsert = "";
        for (int i = 0; i <= length + textToInsert.length(); i++) {
            if (i == cursor) {
                for (int j = 0; j < textToInsert.length(); j++) {
                    textWithInsert += textToInsert.charAt(j);
                }
            }
            if (i < length) {
                textWithInsert += text.charAt(i);
            }
        }
        this.editorState = new EditorState(textWithInsert, cursor);
        this.editorState.setCursorPosition(cursor + textToInsert.length());
        System.out.println(this.toString());

    }

    public void undo() {

        if (!undoArr.empty()) {
            this.redo.push(this.editorState);
            EditorState undoState = this.undoArr.pop();
            this.editorState = undoState;
        }

        System.out.println("After undo(): " + this.toString() + "\n");

    }

    public void redo() {

        if (!redo.empty()) {
            this.undoArr.push(this.editorState);
            EditorState redoState = this.redo.pop();
            this.editorState = redoState;
        }
        System.out.println("After redo(): " + this.toString() + "\n");

    }

    public EditorState getEditorState() {
        return this.editorState;
    }

    @Override
    public String toString() {
        String text = getEditorState().getText();
        String out = "";
        int cursor = editorState.getCursorPosition();
        for (int i = 0; i <= text.length(); i++) {
            if (cursor == i) {
                out += "_";
            }
            if (i < text.length()) {
                out += text.charAt(i);
            }
        }
        return out;
    }
}