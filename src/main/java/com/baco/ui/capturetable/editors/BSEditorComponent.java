package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.BSCaptureTable;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import java.awt.Component;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface arquetipo para los componentes que pueden incrustarse en la tabla 
 * de captura
 * @author dnahuat
 * 
 */
public interface BSEditorComponent {

   /**
    * Obtiene el nombre del campo que edita
    *
    * @return nombre del campo
    */
   public String getName();

   /**
    * Obtiene la instancia del componente
    *
    * @return instancia
    */
   public Component getComponent();

   /**
    * Cambia el valor del componente
    *
    * @param value valor
    */
   public void setComponentValue(Object value);

   /**
    * Obtiene el valor del componente
    *
    * @return valor
    */
   public Object getComponentValue();

   /**
    * Obtiene el valor en String
    *
    * @return valor en String
    */
   public String getValueAsString();

   /**
    * Inicializa o limpia el componente
    */
   public void initOrClear();

   /**
    * Determina si esta activo el componente, si no esta activo este no
    * sera mostrado en la tabla
    *
    * @return true si esta activo
    */
   public boolean isActive();

   /**
    * Activa o desactiva el componente
    *
    * @param active true si el componente se activa y false si se desactiva
    */
   public void setActive(boolean active);

   /**
    * Determina si el componente permite ser editado o no
    *
    * @return true si el componente es editable
    */
   public boolean isEditable();

   /**
    * Cambia de estado editable a no editable y viceversa
    *
    * @param editable true si es editable
    */
   public void setEditable(boolean editable);

   /**
    * Agrega un escucha interesado en cuando un componente termina su edicion
    *
    * @param listener escucha a agregar
    */
   public void addEditFinishedListener(BSEditFinishedListener listener);

   /**
    * Elimina un escucha de edicion terminada
    *
    * @param listener escucha a eliminar
    */
   public void removeEditFinishedListener(BSEditFinishedListener listener);

   /**
    * Establece la instancia de la tabla donde se encuentra cargado
    *
    * @param table
    */
   public void setTable(BSCaptureTable table);

   /**
    * Obtiene la instancia de la tabla donde se encuentra cargado el
    * componente
    *
    * @return
    */
   public BSCaptureTable getTable();

   /**
    * Termina la edicion del componente
    */
   public void finishEdit();

   /**
    * Cambia la etiqueta del componente
    *
    * @param label la nueva etiqueta
    */
   public void setLabel(String label);

   /**
    * Obtiene la etiqueta del componente
    *
    * @return etiqueta del componente
    */
   public String getLabel();

   /**
    * Comienza la edicion con el componente
    */
   public void beginEditing();

   /**
    * Determina si se va a editar el siguiente componente al finalizar la
    * edicion de este componente
    *
    * @return true si se editara el siguiente
    */
   public boolean isEditNextComponent();

   /**
    * Activa o desactiva la edicion del siguiente componente
    *
    * @param edit true si se editara el siguiente componente
    */
   public void setEditNextComponent(boolean edit);

   /**
    * Establece el nombre del componente
    *
    * @param name
    */
   public void setName(String name);
}
