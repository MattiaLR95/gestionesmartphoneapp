package it.prova.gestionesmartphoneapp.service;

import java.util.List;

import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneService {

	public List<Smartphone> listAll() throws Exception;

	public Smartphone caricaSingoloElemento(Long id) throws Exception;

	public Smartphone caricaSingoloElementoEagerApps(Long id) throws Exception;

	public void aggiorna(Smartphone smartphoneInstance) throws Exception;

	public void inserisciNuovo(Smartphone smartphoneInstance) throws Exception;

	public void rimuovi(Long idSmartphone) throws Exception;
	
	public void aggiungiApp(App appInstance, Smartphone smartphoneInstance) throws Exception;

	// per injection
	public void setSmartphoneDAO(SmartphoneDAO smartphoneDAO);

	void rimuoviSmartphoneDallaTabellaDiJoin(Long idSmartphone) throws Exception;

}
