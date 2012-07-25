/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package com.baco.ui.scaffold.editors;

import com.baco.ui.components.BSAutoCompleteComboBox;
import com.baco.ui.components.BSAutoCompleteEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.baco.ui.components.BSDateField;
import com.baco.ui.components.BSPasswordWidget;
import com.baco.ui.containers.BSDefaultDialog;
import com.baco.ui.containers.BSTitledDialog;
import com.baco.ui.core.BSCoreFactory;
import com.baco.ui.renderers.BSBooleanSwitchEditor;
import com.baco.ui.scaffold.annotations.EditorAttribute;
import com.baco.ui.scaffold.annotations.MaximumLength;
import com.baco.ui.scaffold.annotations.MinimumLength;
import com.baco.ui.scaffold.annotations.PasswordEdit;
import com.baco.ui.scaffold.annotations.RequiredField;
import com.baco.ui.scaffold.annotations.Scaffold;
import com.baco.ui.scaffold.annotations.ValidPattern;
import com.baco.ui.util.BSMD5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * Editor por defecto para entidades
 * @author dnahuat
 * @param <T> La clase de entidad que maneja este editor
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class DefaultEditor<T> extends BSTitledDialog implements Editor {

   private List<EditorListener> listeners;
   private Map<String, List<?>> listMap;
   private T editedEntity;
   private Class editedClass;
   private OpMode opMode;
   private BSDefaultDialog content;
   private ActionListener okAction;
   private JButton okButton;
   private ActionListener restoreAction;
   private JButton restoreButton;
   private ActionListener cancelAction;
   private JButton cancelButton;
   private ActionListener nextAction;
   private JButton nextButton;
   private ActionListener prevAction;
   private JButton prevButton;
   private ActionListener firstAction;
   private JButton firstButton;
   private ActionListener lastAction;
   private JButton lastButton;
   private StringBuffer errorMessages;

   /* Reflection Attributes */
   private List<Field> editableFields;
   private Map<Field, JComponent> fieldToComponentMap;
   private Map<Field, JComponent> fieldToLabelMap;

   public DefaultEditor(Class<?> editedClass) {
      listeners = new ArrayList<EditorListener>();
      listMap = new HashMap<String, List<?>>();
      this.opMode = OpMode.CREATE;
      try {
         this.editedEntity = (T) editedClass.newInstance();
         this.editedClass = editedClass;
         initComponents();
      } catch (InstantiationException ex) {
         content = new BSDefaultDialog();
         content.setLayout(new BorderLayout());
         content.setInheritAlpha(false);
         content.setOpaque(false);
         JLabel errorLabel = new JLabel("No se puede generar el dialogo con la entidad " + editedClass.
                 getName() + ". Verifique que el constructor "
                 + "nulo sea público.");
         errorLabel.setForeground(Color.WHITE);
         content.add(errorLabel, BorderLayout.CENTER);
      } catch (IllegalAccessException ex) {
         content = new BSDefaultDialog();
         content.setLayout(new BorderLayout());
         content.setInheritAlpha(false);
         content.setOpaque(false);
         JLabel errorLabel = new JLabel("No se puede generar el dialogo con la entidad " + editedClass.
                 getName() + ". Verifique que el constructor "
                 + "nulo sea público.");
         errorLabel.setForeground(Color.WHITE);
         content.add(errorLabel, BorderLayout.CENTER);
      }
   }

   public DefaultEditor(Class<?> editedClass, Map<String, List<?>> listMap) {
      listeners = new ArrayList<EditorListener>();
      this.listMap = listMap;
      this.opMode = OpMode.CREATE;
      try {
         this.editedEntity = (T) editedClass.newInstance();
         this.editedClass = editedClass;
         initComponents();
      } catch (InstantiationException ex) {
         content = new BSDefaultDialog();
         content.setLayout(new BorderLayout());
         content.setInheritAlpha(false);
         content.setOpaque(false);
         JLabel errorLabel = new JLabel("No se puede generar el dialogo con la entidad " + editedClass.
                 getName() + ". Verifique que el constructor "
                 + "nulo sea público.");
         errorLabel.setForeground(Color.WHITE);
         content.add(errorLabel, BorderLayout.CENTER);
      } catch (IllegalAccessException ex) {
         content = new BSDefaultDialog();
         content.setLayout(new BorderLayout());
         content.setInheritAlpha(false);
         content.setOpaque(false);
         JLabel errorLabel = new JLabel("No se puede generar el dialogo con la entidad " + editedClass.
                 getName() + ". Verifique que el constructor "
                 + "nulo sea público.");
         errorLabel.setForeground(Color.WHITE);
         content.add(errorLabel, BorderLayout.CENTER);
      }
   }

   public DefaultEditor(T editedEntity, boolean readOnly) {
      listeners = new ArrayList<EditorListener>();
      listMap = new HashMap<String, List<?>>();
      this.editedEntity = editedEntity;
      if (readOnly) {
         this.opMode = OpMode.DETAIL;
      } else {
         this.opMode = OpMode.EDIT;
      }
      this.editedClass = editedEntity.getClass();
      initComponents();
   }

   public DefaultEditor(T editedEntity, Map<String, List<?>> listComponents,
                        boolean readOnly) {
      listeners = new ArrayList<EditorListener>();
      this.listMap = listComponents;
      this.editedEntity = editedEntity;
      if (readOnly) {
         this.opMode = OpMode.DETAIL;
      } else {
         this.opMode = OpMode.EDIT;
      }
      this.editedClass = editedEntity.getClass();
      initComponents();
   }

   @Override
   public void resetEditor() {
      this.removeContents();
      fieldExtractor();
      fieldMapping();
      constructEditor();
      this.addContent(content);
      refreshEditor();
   }

   private void initComponents() {
      content = new BSDefaultDialog();
      content.setInheritAlpha(false);
      content.setOpaque(false);
      okButton = new JButton();
      restoreButton = new JButton();
      cancelButton = new JButton();
      nextButton = new JButton();
      prevButton = new JButton();
      firstButton = new JButton();
      lastButton = new JButton();
      setupEvents();
      fieldExtractor();
      fieldMapping();
      constructEditor();
      this.addContent(content);
      refreshEditor();
   }

   @Override
   public Map<String, List<?>> getListMap() {
      return listMap;
   }

   @Override
   public void setListMap(Map<String, List<?>> listMap) {
      this.listMap = listMap;
      resetEditor();
   }

   private void refreshEditor() {
      if (editedEntity != null) {
         inferTitle();
         setupButtons();
         if (opMode == OpMode.CREATE) {
            try {
               editedEntity = (T) editedClass.newInstance();
            } catch (InstantiationException ex) {
               editedEntity = null;
               BSCoreFactory.getCore().showErrorMessage(null,
                                                        "Instancianción inválida",
                                                        "No es posible actualizar usando la entidad proporcionada "
                       + "Este diálogo se cerrara", ex);
               close();
            } catch (IllegalAccessException ex) {
               editedEntity = null;
               BSCoreFactory.getCore().showErrorMessage(null, "Acceso inválido",
                                                        "No es posible actualizar usando la entidad proporcionada "
                       + "Este diálogo se cerrara", ex);
               close();
            }
         }
         readEditorValues();
      }
   }

   @Override
   public void addEditorListener(EditorListener listener) {
      if (!listeners.contains(listener)) {
         listeners.add(listener);
      }
   }

   @Override
   public void removeEditorListener(EditorListener listener) {
      listeners.remove(listener);
   }

   /**
    * Cierra este editor, si la entidad actual y la entidad editada son distintas
    * entonces confirma con el usuario si desea continuar
    */
   @Override
   public void closeEditor() {
      this.close();
   }

   @Override
   public void setEditEntity(Object editedEntity) throws ClassCastException {
      this.editedEntity = (T) editedEntity;
      setOperationMode(OpMode.EDIT);
   }

   @Override
   public void setViewEntity(Object editedEntity) throws ClassCastException {
      this.editedEntity = (T) editedEntity;
      setOperationMode(OpMode.DETAIL);
   }

   @Override
   public void setCreateEntity() {
      setOperationMode(OpMode.CREATE);
   }

   @Override
   public void setEntity(Object entity) throws ClassCastException {
      this.editedEntity = (T) entity;
      refreshEditor();
   }

   private void setOperationMode(OpMode opMode) {
      this.opMode = opMode;
      resetEditor();
   }

   @Override
   public OpMode getOperationMode() {
      return opMode;
   }

   @Override
   public <T> T getEntity(Class<T> klass) throws ClassCastException {
      return (T) editedEntity;
   }

   private void setupEvents() {
      okAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (opMode == OpMode.CREATE) {
               fireSaveEditor();
            }
            if (opMode == OpMode.EDIT) {
               fireApplyChanges();
            }
         }
      };
      /* Actualizamos el valor de este */
      restoreAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            fireReset();
         }
      };
      cancelAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            fireCancel();
         }
      };
      prevAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            firePrev();
         }
      };
      nextAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            fireNext();
         }
      };
      firstAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent ae) {
            fireGotoFirst();
         }
      };
      lastAction = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent ae) {
            fireGotoLast();
         }
      };
   }

   private void setupButtons() {
      okButton.removeActionListener(okAction);
      restoreButton.removeActionListener(restoreAction);
      cancelButton.removeActionListener(cancelAction);
      prevButton.removeActionListener(prevAction);
      nextButton.removeActionListener(nextAction);
      firstButton.removeActionListener(firstAction);
      lastButton.removeActionListener(lastAction);
      okButton.addActionListener(okAction);
      restoreButton.addActionListener(restoreAction);
      cancelButton.addActionListener(cancelAction);
      prevButton.addActionListener(prevAction);
      nextButton.addActionListener(nextAction);
      firstButton.addActionListener(firstAction);
      lastButton.addActionListener(lastAction);
      cancelButton.setText("Cancelar");
      cancelButton.setMnemonic('C');
      restoreButton.setText("Restaurar");
      restoreButton.setMnemonic('R');
      firstButton.setText("<<");
      prevButton.setText("<");
      nextButton.setText(">");
      lastButton.setText(">>");
      firstButton.setVisible(true);
      prevButton.setVisible(true);
      nextButton.setVisible(true);
      lastButton.setVisible(true);
      if (opMode == OpMode.CREATE) {
         okButton.setText("Guardar");
         okButton.setMnemonic('G');
         restoreButton.setText("Limpiar");
         restoreButton.setMnemonic('L');
         okButton.setVisible(true);
         restoreButton.setVisible(true);
         firstButton.setVisible(false);
         prevButton.setVisible(false);
         nextButton.setVisible(false);
         lastButton.setVisible(false);
      } else if (opMode == OpMode.EDIT) {
         okButton.setText("Aplicar");
         okButton.setMnemonic('P');
         okButton.setVisible(true);
         restoreButton.setVisible(true);
      } else if (opMode == OpMode.DETAIL) {
         cancelButton.setText("Cerrar");
         cancelButton.setMnemonic('C');
         okButton.setVisible(false);
         restoreButton.setVisible(false);
      }
   }

   /* Editor constructor */
   private void inferTitle() {
      Annotation annot = editedClass.getAnnotation(Scaffold.class);
      if (annot == null) {
         return;
      }
      Scaffold scaffoldAnnotation = (Scaffold) annot;
      if (opMode == OpMode.CREATE) {
         setTitle("Crear " + scaffoldAnnotation.name());
      } else if (opMode == OpMode.EDIT) {
         setTitle("Editar " + scaffoldAnnotation.name());
      } else if (opMode == OpMode.DETAIL) {
         setTitle("Detalle de " + scaffoldAnnotation.name());
      }
   }

   /**
    * Extrae los atributos de la entidad que aplican para ser campos editables
    */
   private void fieldExtractor() {
      Annotation annot = editedClass.getAnnotation(Scaffold.class);
      if (annot == null) {
         return;
      }
      /* Extraer los campos validos */
      Field[] fields = editedClass.getDeclaredFields();
      editableFields = new ArrayList<Field>();
      for (Field field : fields) {
         if (field.getAnnotation(EditorAttribute.class) != null) {
            editableFields.add(field);
         }
      }
   }

   /**
    * Mapea el campo a sus elementos visuales
    */
   private void fieldMapping() {
      fieldToComponentMap = new HashMap<Field, JComponent>();
      fieldToLabelMap = new HashMap<Field, JComponent>();
      for (Field field : editableFields) {
         field.setAccessible(true);
         EditorAttribute attribute = field.getAnnotation(EditorAttribute.class);
         JLabel label = new JLabel(attribute.label());
         label.setForeground(Color.WHITE);
         fieldToLabelMap.put(field, label);
         /*
          * Aqui creamos el componente en caso de que sea una lista
          */
         String listName = attribute.listName();
         if (!listName.equals("no_list_editor")) {
            ListMode listMode = attribute.listMode();
            List dataList = listMap.get(listName);
            if (dataList != null) {
               if (listMode == ListMode.AUTOCOMPLETECOMBOBOX) {
                  BSAutoCompleteComboBox listComponent = new BSAutoCompleteComboBox();
                  listComponent.setData(dataList);
                  listComponent.setEditable(opMode != OpMode.DETAIL);
                  fieldToComponentMap.put(field, listComponent);
               } else if (listMode == ListMode.AUTOCOMPLETEEDITOR) {
                  // TODO Implementar AutoCompleteEditor
               } else if (listMode == ListMode.COMBOBOX) {
                  // TODO Implementar ComboBox
               } else if (listMode == ListMode.LIST) {
                  // TODO Implementar List
               }
            } else {
               JLabel errorLabel = new JLabel("<La lista '" + listName
                       + "' no se encontro en el mapa de listas>");
               errorLabel.setForeground(Color.WHITE);
               fieldToComponentMap.put(field,
                                       errorLabel);
            }
            /* Ir al siguiente campo, no es necesario procesar mas este */
            field.setAccessible(false);
            continue;
         }
         /*
          * Aqui asignamos un componente default por el tipo de dato cada clase
          * puede implementar esta parte de la forma que desee En caso de no poder
          * extraer el valor del componente entonces es necesario mostrar una
          * leyenda de que no fue posible asignar o mostrar el componente
          */
         Class klass = attribute.klass();
         try {
            /* Asignar editor para tipo de texto */
            if (klass.equals(String.class)) {
               /* Verificar que el campo sea password si lo es usar componente password*/
               PasswordEdit passAnnot = field.getAnnotation(PasswordEdit.class);
               if (passAnnot != null) {
                  BSPasswordWidget passwordComponent = new BSPasswordWidget();
                  passwordComponent.setVisible(opMode != OpMode.DETAIL);
                  fieldToComponentMap.put(field, passwordComponent);
               } else {
                  JTextField stringComponent = new JTextField();
                  stringComponent.setEditable(opMode != OpMode.DETAIL);
                  fieldToComponentMap.put(field, stringComponent);
               }
               /* Asignar editor para tipo de fecha */
            } else if (klass.equals(Date.class)) {
               BSDateField dateComponent = null;
               try {
                  dateComponent = new BSDateField();
               } catch (ParseException ex) {
               }
               dateComponent.setEditable(opMode != OpMode.DETAIL);
               fieldToComponentMap.put(field, dateComponent);
               /* Asignar editor para tipo Lista */
            } else if (klass.equals(boolean.class) || klass.equals(
                    Boolean.class)) {
               BSBooleanSwitchEditor booleanComponent = new BSBooleanSwitchEditor();
               booleanComponent.setEnabled(opMode != OpMode.DETAIL);
               fieldToComponentMap.put(field, booleanComponent);
            }
         } catch (ClassCastException ex) {
            JLabel errorLabel = new JLabel("<" + field.getName() + "> no es " + klass.
                    getName());
            errorLabel.setForeground(Color.WHITE);
            fieldToComponentMap.put(field, errorLabel);
         } catch (IllegalArgumentException ex) {
            JLabel errorLabel = new JLabel("<" + editedClass.getName()
                    + "> no contiene " + field.getName());
            errorLabel.setForeground(Color.WHITE);
            fieldToComponentMap.put(field, errorLabel);
         }
         field.setAccessible(false);
      }
   }

   private void constructEditor() {
      content.removeAll();
      MigLayout layout = new MigLayout("wrap 4, hidemode 2", "[] [] [grow] []");
      content.setLayout(layout);

      /* Add attributes to panel */
      for (Field field : editableFields) {
         field.setAccessible(true);
         PasswordEdit passAnnott = field.getAnnotation(PasswordEdit.class);
         if (passAnnott == null) {
            content.add(fieldToLabelMap.get(field),
                        "gapx 5px, gapy 5px, alignx right, aligny top");
         }
         JComponent component = fieldToComponentMap.get(field);
         if ((component instanceof JTextField) || (component instanceof JLabel)
                 || (component instanceof BSAutoCompleteComboBox)
                 || (component instanceof BSAutoCompleteEditor)
                 || (component instanceof JComboBox)) {
            content.add(component, "span 3, growx, wrap");
         } else if ((component instanceof BSPasswordWidget)) {
            content.add(component, "span 4, growx, align left");
         } else if ((component instanceof BSBooleanSwitchEditor)) {
            content.add(component, "span 3, align left, wrap");
         } else if ((component instanceof JList)) {
            // TODO Implementar con JList
         }
         field.setAccessible(false);
      }
      content.add(firstButton, "split 4, span 2, align left");
      content.add(prevButton, "");
      content.add(nextButton, "");
      content.add(lastButton, "");
      content.add(new JSeparator(), "growx");
      content.add(restoreButton, "split 3, align left");
      content.add(cancelButton, "");
      content.add(okButton, "");
   }

   /**
    * Metodo que debe ser llamado cuando se desee validar un campo del formulario
    * antes de ser guardado
    */
   private boolean validateFields() {
      errorMessages = new StringBuffer();
      boolean returnVal = true;
      for (Field field : editableFields) {
         field.setAccessible(true);
         EditorAttribute attribute = field.getAnnotation(EditorAttribute.class);
         Class klass = attribute.klass();
         /* Validar los listados */
         String listName = attribute.listName();
         if (!listName.equals("no_list_editor")) {
            List dataList = listMap.get(listName);
            if (dataList != null) {
               JComponent component = fieldToComponentMap.get(field);
               if (component instanceof BSAutoCompleteComboBox) {
                  BSAutoCompleteComboBox listComponent = (BSAutoCompleteComboBox) component;
                  if (listComponent.getSelectedItem() == null) {
                     errorMessages.append("- No selecciono nada en el campo ");
                     errorMessages.append(attribute.label());
                     errorMessages.append("\n");
                     returnVal = false;
                  }
               }
               if (component instanceof BSAutoCompleteEditor) {
                  // TODO Implementar AutoCompleteEditor
               }
               if (component instanceof JComboBox) {
                  // TODO Implementar JComboBox
               }
               if (component instanceof JList) {
                  // TODO Implementar JList
               }
            } else {
               errorMessages.append("- La lista '");
               errorMessages.append(listName);
               errorMessages.append("' no existe.\n");
               returnVal = false;
            }
            /* Continuar con el siguiente elemento */
            field.setAccessible(false);
            continue;
         }
         /* Validar los elementos de texto */
         if (klass.equals(String.class)) {
            if (fieldToComponentMap.get(field) instanceof JTextField) {
               JTextField textComponent = (JTextField) fieldToComponentMap.get(
                       field);
               /* Obtener validaciones */
               MinimumLength minLength = field.getAnnotation(MinimumLength.class);
               MaximumLength maxLength = field.getAnnotation(MaximumLength.class);
               ValidPattern validPattern = field.getAnnotation(
                       ValidPattern.class);
               RequiredField requiredField = field.getAnnotation(
                       RequiredField.class);
               /* Verificar todas las validaciones */
               /* Campo requerido */
               if (requiredField != null) {
                  if (textComponent.getText().trim().isEmpty()) {
                     errorMessages.append("-");
                     errorMessages.append(requiredField.message());
                     errorMessages.append("\n");
                     returnVal = false;
                  }
               }
               /* Valor minimo */
               if (minLength != null) {
                  if (textComponent.getText().trim().length()
                          < minLength.value()) {
                     errorMessages.append("-");
                     errorMessages.append(minLength.message());
                     errorMessages.append("\n");
                     returnVal = false;
                  }
               }
               /* Valor maximo */
               if (maxLength != null) {
                  if (textComponent.getText().trim().length()
                          > maxLength.value()) {
                     errorMessages.append("-");
                     errorMessages.append(maxLength.message());
                     errorMessages.append("\n");
                     returnVal = false;
                  }
               }
               /* Expresion regular */
               if (validPattern != null) {
                  try {
                     Pattern pattern = Pattern.compile(validPattern.value());
                     Matcher matcher = pattern.matcher(textComponent.getText());
                     if (!matcher.matches()) {
                        errorMessages.append("-");
                        errorMessages.append(validPattern.message());
                        errorMessages.append("\n");
                        returnVal = false;
                     }
                  } catch (PatternSyntaxException ex) {
                     errorMessages.append(
                             "- El patron regex no es valido para el campo '");
                     errorMessages.append(attribute.label());
                     errorMessages.append("' verifique el modelo de datos.\n");
                     returnVal = false;
                  }
               }
            }
            /* Validar campos password */
            if (fieldToComponentMap.get(field) instanceof BSPasswordWidget) {
               BSPasswordWidget passwordComponent = (BSPasswordWidget) fieldToComponentMap.
                       get(field);
               if (passwordComponent.isEmpty()) {
                  if (opMode == OpMode.CREATE) {
                     errorMessages.append("- La contraseña esta vacia\n");
                     returnVal = false;
                  }
               } else {
                  if (!passwordComponent.isConfirmed()) {
                     errorMessages.append("- Las contraseñas no coinciden.\n");
                     returnVal = false;
                  }
               }
            }
         }
         field.setAccessible(false);
      }
      return returnVal;
   }

   private void readEditorValues() {
      for (Field field : editableFields) {
         field.setAccessible(true);
         EditorAttribute attribute = field.getAnnotation(EditorAttribute.class);
         /* Obtenemos y asignamos los valores de los campos */
         Class klass = attribute.klass();
         /* Llenar la informacion de una lista */
         String listName = attribute.listName();
         if (!listName.equals("no_list_editor")) {
            ListMode listMode = attribute.listMode();
            List dataList = listMap.get(listName);
            if (dataList != null) {
               /* Por cada tipo de lista escribir la informacion */
               if (listMode == ListMode.AUTOCOMPLETECOMBOBOX) {
                  BSAutoCompleteComboBox listComponent = (BSAutoCompleteComboBox) fieldToComponentMap.
                          get(field);
                  listComponent.setData(dataList);
                  try {
                     Object valueObject = field.get(editedEntity);
                     listComponent.setSelectedItem(valueObject);
                  } catch (IllegalAccessException e) {
                     String error = "Error al obtener el campo "
                             + field.getName();
                     listComponent.addItem(error);
                     listComponent.setSelectedItem(error);
                  } catch (IllegalArgumentException e) {
                     String error = "Error al obtener el campo "
                             + field.getName();
                     listComponent.addItem(error);
                     listComponent.setSelectedItem(error);
                  }
               } else if (listMode == ListMode.AUTOCOMPLETEEDITOR) {
                  // TODO Implementar AutoCompleteEditor
               } else if (listMode == ListMode.COMBOBOX) {
                  // TODO Implementar ComboBox
               } else if (listMode == ListMode.LIST) {
                  // TODO Implementar List
               }
            }
            /* Ir al siguiente campo */
            field.setAccessible(false);
            continue;
         }

         /* Leer en caso de que sea un campo estandar */
         try {
            /* Asignar valor a un editor de texto */
            if (klass.equals(String.class)) {
               String value = "";
               Object valueObject = field.get(editedEntity);
               if (valueObject != null) {
                  value = (String) valueObject;
               }
               /* Si es un texto normal */
               if (fieldToComponentMap.get(field) instanceof JTextField) {
                  JTextField stringComponent = (JTextField) fieldToComponentMap.
                          get(field);
                  stringComponent.setText(value);
               }
               /* Si es un texto de password */
               if (fieldToComponentMap.get(field) instanceof BSPasswordWidget) {
                  BSPasswordWidget passwordComponent = (BSPasswordWidget) fieldToComponentMap.
                          get(field);
                  passwordComponent.clearPassword();
               }
               /* Asignar editor para tipo de fecha */
            } else if (klass.equals(Date.class)) {
               Date value = Calendar.getInstance().getTime();
               Object valueObject = field.get(editedEntity);
               if (valueObject != null) {
                  value = (Date) valueObject;
               }
               if (fieldToComponentMap.get(field) instanceof BSDateField) {
                  BSDateField dateComponent = (BSDateField) fieldToComponentMap.
                          get(field);
                  dateComponent.setTime(value);
               }
               /* Asignar editor para tipo Booleano */
            } else if (klass.equals(boolean.class) || klass.equals(
                    Boolean.class)) {
               Boolean value = false;
               Object valueObject = field.get(editedEntity);
               if (valueObject != null) {
                  value = (Boolean) valueObject;
               }
               if (fieldToComponentMap.get(field) instanceof BSBooleanSwitchEditor) {
                  BSBooleanSwitchEditor booleanComponent = (BSBooleanSwitchEditor) fieldToComponentMap.
                          get(field);
                  booleanComponent.setRawValue(value);
               }
            }
         } catch (ClassCastException ex) {
            JLabel errorLabel = new JLabel("<" + field.getName() + "> no es " + klass.
                    getName());
            errorLabel.setForeground(Color.WHITE);
            fieldToComponentMap.put(field, errorLabel);
         } catch (IllegalArgumentException ex) {
            JLabel errorLabel = new JLabel("<" + editedEntity.getClass()
                    + "> no contiene " + field.getName());
            errorLabel.setForeground(Color.WHITE);
            fieldToComponentMap.put(field, errorLabel);
         } catch (IllegalAccessException ex) {
            JLabel errorLabel = new JLabel("<" + field.getName()
                    + "> no es public");
            errorLabel.setForeground(Color.WHITE);
            fieldToComponentMap.put(field, errorLabel);
         }
         field.setAccessible(false);
      }
   }

   /**
    * Extrae el valor de los componentes y los asigna a los atributos
    * correspondientes en la entidad.
    */
   private void commitEditorValues() {
      for (Field field : editableFields) {
         field.setAccessible(true);
         EditorAttribute attribute = field.getAnnotation(EditorAttribute.class);
         Class klass = attribute.klass();
         /* Extraemos el valor del componente de lista */
         String listName = attribute.listName();
         if (!listName.equals("no_list_editor")) {
            ListMode listMode = attribute.listMode();
            List dataList = listMap.get(listName);
            if (dataList != null) {
               if (listMode == ListMode.AUTOCOMPLETECOMBOBOX) {
                  BSAutoCompleteComboBox listComponent = (BSAutoCompleteComboBox) fieldToComponentMap.
                          get(field);
                  Object value = listComponent.getSelectedItem();
                  try {
                     field.set(editedEntity, value);
                  } catch (IllegalAccessException ex) {
                     BSCoreFactory.getCore().showErrorMessage(null,
                                                              "Entidad inválida",
                                                              "La entidad " + editedEntity.
                             getClass().getName() + " no tiene el campo " + field.
                             getName() + " de tipo " + klass.getName()
                             + ", y usando la lista '" + listName + "'."
                             + " Verifique sus declaraciones.",
                                                              ex);
                  }
               } else if (listMode == ListMode.AUTOCOMPLETEEDITOR) {
                  // TODO Implementar AutoCompleteEditor
               } else if (listMode == ListMode.COMBOBOX) {
                  // TODO Implementar ComboBox
               } else if (listMode == ListMode.LIST) {
                  // TODO Implementar List
               }
            }
            /* Ir al siguiente campo, no es necesario procesar mas este */
            field.setAccessible(false);
            continue;
         }
         /* Extraemos el valor de tipo estandar */
         try {
            /* Extraer el valor de los tipos de texto */
            if (klass.equals(String.class)) {
               if (fieldToComponentMap.get(field) instanceof JTextField) {
                  JTextField stringComponent = (JTextField) fieldToComponentMap.
                          get(field);
                  String value = stringComponent.getText();
                  field.set(editedEntity, value);
               }
               /* En caso de componente de password */
               if (fieldToComponentMap.get(field) instanceof BSPasswordWidget) {
                  BSPasswordWidget passwordComponent = (BSPasswordWidget) fieldToComponentMap.
                          get(field);
                  if (!passwordComponent.isEmpty()) {
                     String value = new String(passwordComponent.getPassword());
                     value = BSMD5.encrypt(value);
                     field.set(editedEntity, value);
                  }
               }
               /* Extraer valor del componente de fecha de fecha */
            } else if (klass.equals(Date.class)) {
               if (fieldToComponentMap.get(field) instanceof BSDateField) {
                  BSDateField dateComponent = (BSDateField) fieldToComponentMap.
                          get(field);
                  Date value = dateComponent.getTime();
                  field.set(editedEntity, value);
               }
               /* Extraer valor del componente booleano */
            } else if (klass.equals(boolean.class) || klass.equals(
                    Boolean.class)) {
               if (fieldToComponentMap.get(field) instanceof BSBooleanSwitchEditor) {
                  BSBooleanSwitchEditor booleanComponent = (BSBooleanSwitchEditor) fieldToComponentMap.
                          get(field);
                  Boolean value = booleanComponent.getValue();
                  field.set(editedEntity, value);
               }
            }
         } catch (IllegalAccessException ex) {
            BSCoreFactory.getCore().showErrorMessage(null, "Entidad inválida",
                                                     "La entidad " + editedEntity.
                    getClass().getName() + " no tiene el campo "
                    + field.getName() + " de tipo " + klass.getName()
                    + ". Verifique sus declaraciones",
                                                     ex);
         }
         field.setAccessible(false);
      }
   }

   /* Action Buttons */
   /**
    * Esta accion dispara un evento de acuerdo a el modo de operacion de este
    * editor
    */

   /* Disparo de eventos */
   private void fireSaveEditor() {
      if (validateFields()) {
         commitEditorValues();
         for (EditorListener listener : listeners) {
            listener.saveEditor(new EditorEvent<T>(editedEntity, this));
         }
      } else {
         BSCoreFactory.getCore().showErrorMessage(null, "Datos invalidos", errorMessages.
                 toString());
      }
   }

   private void fireCancel() {
      for (EditorListener listener : listeners) {
         listener.cancel(new EditorEvent<T>(editedEntity, this));
      }
   }

   private void fireApplyChanges() {
      if (validateFields()) {
         commitEditorValues();
         for (EditorListener listener : listeners) {
            listener.applyChanges(new EditorEvent<T>(editedEntity, this));
         }
      } else {
         BSCoreFactory.getCore().showErrorMessage(null, "Datos invalidos", errorMessages.
                 toString());
      }
   }

   private void fireReset() {
      for (EditorListener listener : listeners) {
         listener.reset(new EditorEvent<T>(editedEntity, this));
      }
   }

   private void fireGotoFirst() {
      for (EditorListener listener : listeners) {
         listener.gotoFirst(new EditorEvent<T>(editedEntity, this));
      }
   }

   private void fireGotoLast() {
      for (EditorListener listener : listeners) {
         listener.gotoLast(new EditorEvent<T>(editedEntity, this));
      }
   }

   private void fireNext() {
      for (EditorListener listener : listeners) {
         listener.next(new EditorEvent<T>(editedEntity, this));
      }
   }

   private void firePrev() {
      for (EditorListener listener : listeners) {
         listener.prev(new EditorEvent<T>(editedEntity, this));
      }
   }
}
