package it.prova.gestionesmartphoneapp.dao.app;

import it.prova.gestionesmartphoneapp.dao.IBaseDAO;
import it.prova.gestionesmartphoneapp.model.App;

public interface AppDAO extends IBaseDAO<App> {

	public App findByIdFetchingSmartphone(Long id) throws Exception;
	
	public void deleteAppFromJoinTable(Long id) throws Exception;


}
