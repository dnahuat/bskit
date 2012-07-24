package com.baco.ui.core;

import java.awt.Component;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (close) : Ahora devuelve boolean para verificar proceso de cerrado
 * 2011-04-13 (beforeLoad,onLoad,afterLoad) : Se agrega retorno de bandera para
 *    confirmar continuacion de la carga del componente.
 *    Se agrega manejo de errores
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para componentes o modulos que requieren capacidad grafica. Extiende
 * la interface BSElement
 * @author dnahuat
 */
public interface BSCoreComponent extends BSElement {

   /**
    * Accion Precarga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   boolean beforeLoad() throws Exception;

   /**
    * Accion Carga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   boolean onLoad() throws Exception;

   /**
    * Accion Postcarga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   void afterLoad();

   boolean isSingleInstance();

   /**
    * <p>
    * En milisegundos se define el tiempo que se va a esperar antes de ejecutar el metodo onLoad este elemento
    * </p>
    * @param delay
    */
   void setLoadDelay(long delay);

   /**
    * <p>
    * Debe devolver el tiempo que se va a esperar antes de ejecutar el metodo onLoad de este elemento
    * </p>
    * @return
    */
   long getLoadDelay();

   /**
    * <p>
    * Transfiere el foco de la aplicacion a este componente
    * </p>
    *
    * @return True si el foco se puede transferir, false si el componente no es
    *         visible en el frame actual
    */
   boolean requestFocusInWindow();

   /**
    * Invoca el proceso de cerrado de este componente
    * @return True si se cerro exitosamente, false de otro modo
    */
   boolean close();

   /**
    * Devuelve este componente grafico como una instancia de {@link Component}.
    * Este es un metodo anexado por conveniencia
    *
    * @return Instancia casteada a Component
    */
   Component getAsComponent();

   /**
    * Identificador unico del componente en BSKit
    * @return 
    */
   BSOID getOID();
}
