public class EditorState {

    private String text;
    private int cursor;

    public EditorState() {
        this.text = "";
        this.cursor = 0;
    }

    public EditorState(String text) {
        this.text = text;
        cursor = 0;
    }

    public EditorState(String text, int cursor) {
        this.text = text;
        this.cursor = cursor;
    }

    public String getText() {
        return this.text;
    }

    public int getCursorPosition() {
        return this.cursor;
    }

    // public int characterCount() {
    //     int charLength = 0;

    //     try {
    //         File mainFile = new File(this.fileName);
    //         FileReader fr = new FileReader(mainFile);  
    //         BufferedReader br = new BufferedReader(fr);
    //         String line;
    

    //         while ((line = br.readLine()) != null) {
    //             charLength += line.length();
    //         }

    //         fr.close(); 
    //         br.close();  

    //     } catch (Exception e) {
    //         System.out.println("Exception: " + e);
    //     }
    //     return charLength;
    // }

    public int setCursorPosition(int newPosition) {

        if (newPosition < getText().length()) {
            this.cursor = newPosition;
        } else if (newPosition >= getText().length()) {
            this.cursor = getText().length() - 1;
        } else {
            this.cursor = 0;
        }

        return this.cursor;
    }

    public void moveCursorForward() {
        cursor = getCursorPosition();
        setCursorPosition(this.cursor + 1);
    }

    public void moveCursorBackward() {
        cursor = getCursorPosition();
        setCursorPosition(this.cursor - 1);
    }

    @Override
    public EditorState clone() {
        EditorState clonedState = new EditorState(text, cursor);
        String text = new String(this.text);
        clonedState.text = text;
        return clonedState;
    }
}