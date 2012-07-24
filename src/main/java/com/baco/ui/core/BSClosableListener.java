package com.baco.ui.core;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Escucha para objetos que quieran recibir una notificacion de cierre, al finalizar
 * el nucleo de BSKit
 * @author dnahuat
 */
public interface BSClosableListener {

   public boolean tryClose();
}
