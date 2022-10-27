package it.prova.gestionesmartphoneapp.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public class SmartphoneServiceImpl implements SmartphoneService {

	private SmartphoneDAO smartphoneDAO;

	@Override
	public List<Smartphone> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return smartphoneDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Smartphone caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return smartphoneDAO.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Smartphone caricaSingoloElementoEagerApps(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return smartphoneDAO.findByIdFetchingApp(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Smartphone smartphoneInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			smartphoneDAO.update(smartphoneInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Smartphone smartphoneInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			smartphoneDAO.insert(smartphoneInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Long idSmartphone) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			smartphoneDAO.delete(smartphoneDAO.get(idSmartphone));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void setSmartphoneDAO(SmartphoneDAO smartphoneDAO) {
		this.smartphoneDAO = smartphoneDAO;
	}

	@Override
	public void aggiungiApp(App appInstance, Smartphone smartphoneInstance)
			throws Exception {
		// questo è come una connection
				EntityManager entityManager = EntityManagerUtil.getEntityManager();

				try {
					// questo è come il MyConnection.getConnection()
					entityManager.getTransaction().begin();

					// uso l'injection per il dao
					smartphoneDAO.setEntityManager(entityManager);
					
					appInstance=entityManager.merge(appInstance);
					
					smartphoneInstance=entityManager.merge(smartphoneInstance);

					// eseguo quello che realmente devo fare
					smartphoneInstance.getApps().add(appInstance);

					entityManager.getTransaction().commit();
				} catch (Exception e) {
					entityManager.getTransaction().rollback();
					e.printStackTrace();
					throw e;
				} finally {
					EntityManagerUtil.closeEntityManager(entityManager);
				}
	}
	
	@Override
	public void rimuoviSmartphoneDallaTabellaDiJoin(Long idSmartphone) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			smartphoneDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			smartphoneDAO.deleteSmartphoneFromJoinTable(idSmartphone);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

}
