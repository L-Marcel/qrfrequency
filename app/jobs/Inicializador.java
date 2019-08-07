package jobs;

import models.Administrador;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

	@Override
	public void doJob() throws Exception {
	
		if(Administrador.count() == 0) {
			
			Administrador adm1 = new Administrador();
			adm1.matricula = "admin";
			adm1.senha = "admin";
			adm1.save();
		}
	}
}
