package com.baco.ui.services;

/**
 * Las implementaciones de esta interfaz se encargan de autenticar a los
 * usuarios que acceden a los sistemas
 * 
 * 
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public interface BSLoginService {

   /**
    * realiza la autenticaci&oacute;n de usuario
    *
    * @param username Nombre de usuario
    * @param password Contrase&ntilde;a
    * @param schema determina un esquema sobre el cual el usuario va a trabajar
    * este esquema puede tener un significado distinto seg&uacute;n la
    * aplicaci&oacute;n
    * @return true si se autentica el usuario
    */
   boolean login(String username, String password, Object schema);

   /**
    * Indica si la sesion esta iniciada
    * @return true si el usuario esta autenticado
    */
   boolean isLogged();

   /**
    * Cierra la sesion con el usuario logueado.
    */
   void closeSession();

   /**
    * Se ejecuta para cambiar el perfil del usuario
    */
   void changeProfile();
}
