package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import com.baco.ui.capturetable.BSCaptureTable;
import com.baco.ui.components.BSAutoCompleteComboBox;
import com.baco.ui.components.BSAutoCompleteEditor;
import com.baco.ui.components.BSAutoTransferListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente combo con autocompletado
 * @author dnahuat
 * 
 */
public class BSAutoCompleteComboBoxEditorComponent extends BSAutoCompleteComboBox
        implements BSEditorComponent {

   public static final int SELECT_FIRST = 0;
   public static final int CLEAR_LIST = 1;
   private boolean activate = true;
   private BSCaptureTable table;
   private List<BSEditFinishedListener> listeners = new ArrayList();
   private String label;
   private boolean selectedFromPopup = false;
   private boolean editNextComponent = true;
   public int clearMode = 0;

   public BSAutoCompleteComboBoxEditorComponent() {
      super();

      addPopupMenuListener(new PopupMenuListener() {
         @Override
         public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            selectedFromPopup = true;
         }
         @Override
         public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            selectedFromPopup = false;
         }
         @Override
         public void popupMenuCanceled(PopupMenuEvent e) {
            selectedFromPopup = false;
         }
      });

      addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (selectedFromPopup) {
               finishEdit();
            }
         }
      });

      addAutoTransferListener(new BSAutoTransferListener() {
         @Override
         public void focusTransfered() {
            finishEdit();
         }
      });

      final BSEditorComponent instance = this;

      ((BSAutoCompleteEditor) editor).addKeyListener(new KeyAdapter() {

         @Override
         public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_UP) {
               finishEdit();
               table.requestBackward(instance);
            }
         }
      });
   }

   @Override
   public Component getComponent() {
      return this;
   }

   @Override
   public void setComponentValue(Object value) {
      this.setSelectedItem(value);
   }

   @Override
   public Object getComponentValue() {
      return this.getSelectedItem();
   }

   @Override
   public String getValueAsString() {
      if (this.getModel().getSize() == 0) {
         return "";
      } else {
         return this.getSelectedItem().toString();
      }
   }

   @Override
   public void initOrClear() {
      if (clearMode == SELECT_FIRST) {
         if (this.getItemCount() > 0) {
            this.setSelectedIndex(0);
         }
      } else {
         this.setData(new ArrayList());
      }
   }

   @Override
   public boolean isActive() {
      return this.activate;
   }

   @Override
   public void setActive(boolean active) {
      this.activate = active;
   }

   @Override
   public void addEditFinishedListener(BSEditFinishedListener listener) {
      listeners.add(listener);
   }

   @Override
   public void removeEditFinishedListener(BSEditFinishedListener listener) {
      listeners.remove(listener);
   }

   @Override
   public void setTable(BSCaptureTable table) {
      this.table = table;
   }

   @Override
   public BSCaptureTable getTable() {
      return this.table;
   }

   @Override
   public void finishEdit() {
      this.selectedFromPopup = false;

      for (BSEditFinishedListener listener : listeners) {
         listener.editFinished(new BSEditFinishedEvent(this));
      }
   }

   @Override
   public void setLabel(String label) {
      this.label = label;
   }

   @Override
   public String getLabel() {
      if (label == null) {
         return this.getName();
      } else {
         return this.label;
      }
   }

   @Override
   public void beginEditing() {
      this.requestFocus();
   }

   @Override
   public void setEditNextComponent(boolean edit) {
      editNextComponent = edit;
   }

   @Override
   public boolean isEditNextComponent() {
      return editNextComponent;
   }

   public void setClearMode(int clearMode) {
      this.clearMode = clearMode;
   }

   public int getClearMode() {
      return clearMode;
   }
}
