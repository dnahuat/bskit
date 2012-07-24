package com.baco.ui.tableeditors;

import java.awt.Font;
import java.util.List;
import javax.swing.JTable;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import com.baco.ui.components.BSDateField;
import javax.swing.event.CellEditorListener;

/**
 * Editor para fechas
 * @author dnahuat
 *
 * CHANGELOG
 * -----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSDateFieldEditor extends BSDateField implements TableCellEditor {

   protected transient List<CellEditorListener> listeners = new ArrayList();
   private String oldValue;
   protected transient boolean editing;
   protected transient int currentRow;
   private SimpleDateFormat sdf;
   private boolean isKeyFocus = false;

   public BSDateFieldEditor() throws ParseException {
      sdf = new SimpleDateFormat("dd/MM/yyyy");
      setFont(getFont().deriveFont(Font.BOLD));
      addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent fe) {
            if (!isKeyFocus) {
               selectAll();
            }
         }

         @Override
         public void focusLost(FocusEvent fe) {
         }
      });
      addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent ae) {
            stopCellEditing();
         }
      });
   }

   @Override
   public Component getTableCellEditorComponent(JTable table,
                                                Object value,
                                                boolean isSelected,
                                                int row,
                                                int col) {
      if (value instanceof String && value != null) {
         editing = true;
         try {
            setTime(sdf.parse((String) value));
         } catch (ParseException ex) {
            setTime(null);
         }
         this.oldValue = (String) value;
         currentRow = row;
      }
      this.setFont(table.getFont());
      this.setVisible(true);
      return this;
   }

   public int getCurrentRow() {
      return currentRow;
   }

   @Override
   public Object getCellEditorValue() {
      return getText();
   }

   @Override
   public boolean isCellEditable(EventObject eo) {
      if (eo instanceof MouseEvent) {
         isKeyFocus = false;
         MouseEvent me = (MouseEvent) eo;
         if (me.getClickCount() == 2 && me.getButton() == 1) {
            return true;
         } else {
            return false;
         }
      } else {
         if (eo instanceof KeyEvent) {
            isKeyFocus = true;
            KeyEvent ke = (KeyEvent) eo;
            if ((ke.getKeyCode() >= KeyEvent.VK_0 && ke.getKeyCode()
                    <= KeyEvent.VK_9)
                    || (ke.getKeyCode() >= KeyEvent.VK_NUMPAD0 && ke.getKeyCode()
                    <= KeyEvent.VK_NUMPAD9)
                    || (ke.getKeyCode() == KeyEvent.VK_SPACE)) {
               return true;
            } else {
               return false;
            }
         }
      }
      return true;
   }

   @Override
   public boolean shouldSelectCell(EventObject eo) {
      return true;
   }

   @Override
   public boolean stopCellEditing() {
      setVisible(false);
      editing = false;
      fireEditingStopped();
      return true;
   }

   @Override
   public void cancelCellEditing() {
      editing = false;
      setVisible(false);
      fireCancelCellEditing();
   }

   public String getOldValue() {
      return this.oldValue;
   }

   private void fireEditingStopped() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.get(i)).editingStopped(ce);
      }
   }

   private void fireCancelCellEditing() {
      setTime(null);
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.get(i)).editingCanceled(ce);
      }
   }

   @Override
   public void addCellEditorListener(CellEditorListener listener) {
      if (!listeners.contains(listener)) {
         listeners.add(listener);
      }
   }

   @Override
   public void removeCellEditorListener(CellEditorListener listener) {
      listeners.remove(listener);
   }
}
