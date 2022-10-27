package it.prova.gestionesmartphoneapp.dao.smartphone;

import it.prova.gestionesmartphoneapp.dao.IBaseDAO;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneDAO extends IBaseDAO<Smartphone>{
	
	public Smartphone findByIdFetchingApp(Long id) throws Exception;

	void deleteSmartphoneFromJoinTable(Long idSmartphone) throws Exception;
			
}
