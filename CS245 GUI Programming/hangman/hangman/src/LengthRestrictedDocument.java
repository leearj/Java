/**
 * *************************************************************
 * file: LengthRestrictedDocument.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/17/2016
 *
 * purpose: Extension of PlainDocument to be used with the initials JTextField in GUI. Can be used to limit the amount of characters
 * in a text field.
 *
 ***************************************************************
 */

package hangman;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public final class LengthRestrictedDocument extends PlainDocument {

    private final int limit;

    public LengthRestrictedDocument(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offs, str, a);
        }
    }
}
