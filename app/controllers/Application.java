package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
    	if(session.get("matriculaAdmin") == null){
    		indexUsuario();
    	}else{
    		 render();
    	}
       
    }
    
    public static void indexUsuario() {
    	if(session.get("usuarioEmail") == null){
    		index();
    	}else{
    		 render();
    	}
    }
    
    

}