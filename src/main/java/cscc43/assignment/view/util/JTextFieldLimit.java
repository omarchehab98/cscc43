package cscc43.assignment.view.util;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;

/**
 * @see https://stackoverflow.com/a/10136854
 */
public class JTextFieldLimit extends PlainDocument {
    private int limit;
    
    public JTextFieldLimit(int limit) {
      super();
      this.limit = limit;
    }
  
    public JTextFieldLimit(int limit, boolean upper) {
      super();
      this.limit = limit;
    }
  
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (str == null) {
        return;
      }
  
      if ((getLength() + str.length()) <= limit) {
        super.insertString(offset, str, attr);
      }
    }
  }
  