package com.baco.ui.scaffold;

import com.baco.ui.containers.BSDialog;
import com.baco.ui.core.BSCoreFactory;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import com.baco.ui.util.BSFilterTable;
import com.baco.ui.scaffold.editors.Editor;
import com.baco.ui.scaffold.enums.ScaffoldType;
import com.baco.ui.datamodels.BSBeanTableModel;
import com.baco.ui.scaffold.annotations.Scaffold;
import java.lang.reflect.InvocationTargetException;
import com.baco.ui.datamodels.BSTableModelAttributeList;
import com.baco.ui.scaffold.data.Provider;
import com.baco.ui.scaffold.editors.EditorAdapter;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Clase de scaffold default
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class DefaultScaffold<T> extends JXPanel {

   private Editor editEditor = null;
   private Editor createEditor = null;
   private Editor detailEditor = null;
   private Class<T> entityClass = null;
   private Provider dataProvider = null;
   private ScaffoldType scaffoldType;
   private Scaffold scaffoldAnnotation;
   private boolean validScaffold = false;
   private BSTableModelAttributeList attributeList = null;
   private Map<String, List<?>> listMap = new HashMap<String, List<?>>();
   private BSBeanTableModel model = null;
   /* componentes graficos */
   private JXTable table;
   private JButton createButton;
   private JButton editButton;
   private JButton deleteButton;
   private JButton detailButton;
   private JTextField searchField;
   private JComboBox fields;

   public DefaultScaffold(Class<T> entityClass, Provider dataProvider,
                          Map<String, List<?>> listMap) {
      this(entityClass, dataProvider);
      this.listMap = listMap;
   }

   public DefaultScaffold(Class<T> entityClass, Provider dataProvider) {
      this.entityClass = entityClass;
      this.dataProvider = dataProvider;
      validScaffold = true;
      scaffoldAnnotation = entityClass.getAnnotation(Scaffold.class);
      if (scaffoldAnnotation != null) {
         // TODO Reportar errores de los catchs en este constructor
			/* Obtener tipo de scaffold */
         scaffoldType = scaffoldAnnotation.type();
         /* Instanciar editores */
         if (scaffoldAnnotation.create()) {
            try {
               createEditor = (Editor) scaffoldAnnotation.createForm().
                       getConstructor(Class.class).newInstance(entityClass);
            } catch (InstantiationException ex) {
               validScaffold = false;
            } catch (IllegalAccessException ex) {
               validScaffold = false;
            } catch (IllegalArgumentException ex) {
               validScaffold = false;
            } catch (InvocationTargetException ex) {
               validScaffold = false;
            } catch (NoSuchMethodException ex) {
               validScaffold = false;
            }
         }
         if (scaffoldAnnotation.edit()) {
            try {
               editEditor = (Editor) scaffoldAnnotation.editForm().
                       getConstructor(Class.class).newInstance(entityClass);
            } catch (InstantiationException ex) {
               validScaffold = false;
            } catch (IllegalAccessException ex) {
               validScaffold = false;
            } catch (IllegalArgumentException ex) {
               validScaffold = false;
            } catch (InvocationTargetException ex) {
               validScaffold = false;
            } catch (NoSuchMethodException ex) {
               validScaffold = false;
            }
         }
         if (scaffoldAnnotation.detail()) {
            try {
               detailEditor = (Editor) scaffoldAnnotation.detailForm().
                       getConstructor(Class.class).newInstance(entityClass);
            } catch (InstantiationException ex) {
               validScaffold = false;
            } catch (IllegalAccessException ex) {
               validScaffold = false;
            } catch (IllegalArgumentException ex) {
               validScaffold = false;
            } catch (InvocationTargetException ex) {
               validScaffold = false;
            } catch (NoSuchMethodException ex) {
               validScaffold = false;
            }
         }
         /* Obtener modelo y atributos */
         try {
            attributeList = (BSTableModelAttributeList) scaffoldAnnotation.
                    attributeList().newInstance();
            model = new DefTableModel();
            model.setAttributeList(attributeList);
         } catch (InstantiationException ex) {
            validScaffold = false;
         } catch (IllegalAccessException ex) {
            validScaffold = false;
         }
      }
   }

   public void beforeLoad() {
      initComponents();
   }

   public void onLoad() {
      if (model != null && dataProvider != null) {
         model.addAll(dataProvider.getList());
      }
   }

   public void afterLoad() {
      setupEvents();
   }

   private void initComponents() {
      createButton = new JButton("Nuevo");
      editButton = new JButton("Editar");
      deleteButton = new JButton("Eliminar");
      detailButton = new JButton("Detalle");
      if (!scaffoldAnnotation.create()) {
         createButton.setVisible(false);
      }
      if (!scaffoldAnnotation.edit()) {
         editButton.setVisible(false);
      }
      if (!scaffoldAnnotation.detail()) {
         detailButton.setVisible(false);
      }
      if (!scaffoldAnnotation.delete()) {
         deleteButton.setVisible(false);
      }
      MigLayout layout = new MigLayout("wrap 4, hidemode 2", "[] [] [grow] []",
                                       "[] [grow] []");
      this.setLayout(layout);
      this.add(new JLabel("Buscar"), "gapx 5px, gapy 5px, alignx right");
      this.add(searchField, "span 2, growx, gapx 5px, gapy 5px");
      this.add(fields, "gapx 5px, gapy 5px, wrap");
      this.add(table, "span 4, growx, growy, wrap");
      this.add(deleteButton, "gapx 5px, gapy 5px, align left");
      this.add(new JSeparator(), "span 2, growx");
      this.add(detailButton, "split 3, align left");
      this.add(editButton, "");
      this.add(createButton, "");
   }

   private void setupEvents() {
      if (validScaffold) {
         if (scaffoldAnnotation.create()) {
            createEditor.addEditorListener(new EditorAdapter() {
               // TODO Agregar eventos del editor de creacion
            });
            createButton.addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent ae) {
                  createEditor.setListMap(listMap);
                  createEditor.setCreateEntity();
                  BSCoreFactory.getCore().loadAsDialog((BSDialog) createEditor);
               }
            });
         }
         if (scaffoldAnnotation.edit()) {
            editEditor.addEditorListener(new EditorAdapter() {
               // TODO Agregar eventos del editor de edicion
            });
            editButton.addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent ae) {
                  int selected = table.getSelectedRow();
                  if (selected == -1) {
                     return;
                  }
                  selected = table.convertRowIndexToModel(selected);
                  Object selEntity = model.get(selected);
                  editEditor.setListMap(listMap);
                  editEditor.setEditEntity(selEntity);
                  BSCoreFactory.getCore().loadAsDialog((BSDialog) editEditor);
               }
            });
         }
         if (scaffoldAnnotation.detail()) {
            detailEditor.addEditorListener(new EditorAdapter() {
               // Agregar eventos del editor de detalles
            });
            detailButton.addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent ae) {
                  int selected = table.getSelectedRow();
                  if (selected == -1) {
                     return;
                  }
                  selected = table.convertRowIndexToModel(selected);
                  Object selEntity = model.get(selected);
                  detailEditor.setListMap(listMap);
                  detailEditor.setViewEntity(selEntity);
                  BSCoreFactory.getCore().loadAsDialog((BSDialog) editEditor);
               }
            });
         }
         // TODO Agregar evento para el boton de eliminacion
         BSFilterTable.add(fields, searchField, table);
      }
   }

   public void setListMap(Map<String, List<?>> listMap) {
      this.listMap = listMap;
   }

   private class DefTableModel extends BSBeanTableModel<T> {

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
         return false;
      }
   }
}
