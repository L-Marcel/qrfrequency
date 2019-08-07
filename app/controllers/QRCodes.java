package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class QRCodes extends Controller{
	
	public static void geradorQR() {
		
	}
}
