/**
 * *************************************************************
 * file: LengthRestrictedDocument.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/17/2016
 *
 * purpose: Extension of PlainDocument to be used with the Sudoku text fields. Only allows single digit 1-9
 *
 ***************************************************************
 */

package hangman;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public final class SudokuDigitDocument extends PlainDocument {

    private final int limit;

    public SudokuDigitDocument() {
        this.limit = 1;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit && str.charAt(0) >= 49 && str.charAt(0) <= 57) {
            super.insertString(offs, str, a);
        }
    }
}
