package it.prova.gestionesmartphoneapp.dao;

import it.prova.gestionesmartphoneapp.dao.app.AppDAO;
import it.prova.gestionesmartphoneapp.dao.app.AppDAOImpl;
import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAOImpl;

public class MyDaoFactory {

	private static SmartphoneDAO smartphoneDaoInstance = null;
	private static AppDAO appDaoInstance = null;

	public static SmartphoneDAO getSmartphoneDAOInstance() {
		if (smartphoneDaoInstance == null)
			smartphoneDaoInstance = new SmartphoneDAOImpl();

		return smartphoneDaoInstance;
	}

	public static AppDAO getAppDAOInstance() {
		if (appDaoInstance == null)
			appDaoInstance = new AppDAOImpl();

		return appDaoInstance;
	}

}
