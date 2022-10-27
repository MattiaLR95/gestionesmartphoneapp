package it.prova.gestionesmartphoneapp.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.AppService;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.SmartphoneService;

public class TestGestioneSmartphoneApp {

	public static void main(String[] args) {
		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appServiceInstance = MyServiceFactory.getAppServiceInstance();

		try {
			testNuovoSmartphone(smartphoneServiceInstance);
			System.out.println();

			testAggiornamentoSmartphone(smartphoneServiceInstance);
			System.out.println();

			testNuovaApp(appServiceInstance);
			System.out.println();
			
			testAggiornaDataEVersioneApp(appServiceInstance);
			System.out.println();
			
			testDisinstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println();
			
			testInstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println();
			
			testRimozioneSmartphoneAssociatoADueApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testNuovoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println("Inizio testNuovoSmartphone");

		Smartphone nuovoSmartphone = new Smartphone("Xiaomi", "Note 10", 600, "2.4.1");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		if (nuovoSmartphone.getId() == null)
			throw new RuntimeException("test NuovoSmartphone: FALLITO");

		System.out.println("test nuovoSmartphone: COMPLETATO");
	}

	private static void testAggiornamentoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println("Inizio testAggiornamentoSmartphone");

		List<Smartphone> listaDatabase = smartphoneServiceInstance.listAll();
		if (listaDatabase.isEmpty())
			throw new Exception("Attenzione! Database vuoto");
		Smartphone smartphoneDaAggiornare = new Smartphone("Xiaomi", "Note 10", 600, "2.4.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneDaAggiornare);

		String marcaVecchia = smartphoneDaAggiornare.getMarca();

		smartphoneDaAggiornare.setMarca("LG");

		smartphoneServiceInstance.aggiorna(smartphoneDaAggiornare);
		if (smartphoneDaAggiornare.getMarca().equals(marcaVecchia))
			throw new RuntimeException("Test aggiorna: FALLITO");
		System.out.println("Test aggiorna: COMPLETATO");
	}

	private static void testNuovaApp(AppService appServiceInstance) throws Exception {
		System.out.println("Inizio testNuovaApp");

		App nuovApp = new App("Shazam", new Date(), new Date(), "2.4.1");
		appServiceInstance.inserisciNuovo(nuovApp);
		if (nuovApp.getId() == null)
			throw new Exception("Test inserimentoApp: FALLITO");
		System.out.println("Test inserimentoAPP: COMPLETATO");
	}

	private static void testAggiornaDataEVersioneApp(AppService appServiceInstance) throws Exception {
		System.out.println("Inizio testAggiornaDataEVersioneApp");

		List<App> listaDatabase = appServiceInstance.listAll();
		if (listaDatabase.isEmpty())
			throw new Exception("Attenzione! Database vuoto");
		App appDaAggiornare = new App("Shazam", new Date(), new Date(), "2.4.1");
		appServiceInstance.inserisciNuovo(appDaAggiornare);

		String versioneVecchia = appDaAggiornare.getVersione();
		Date dataVecchia = appDaAggiornare.getDataUltimoAggiornamento();

		appDaAggiornare.setVersione("4.5.1");
		appDaAggiornare.setDataUltimoAggiornamento(new SimpleDateFormat("dd-mm-yyyy").parse("25-12-2022"));
		appServiceInstance.aggiorna(appDaAggiornare);
		if (appDaAggiornare.getVersione().equals(versioneVecchia)
				|| appDaAggiornare.getDataUltimoAggiornamento().equals(dataVecchia))
			throw new RuntimeException("Test aggiornaDataEVersioneApp: FALLITO");
		System.out.println("Test aggiornaDataEVersioneApp: COMPLETATO");
	}
	
	private static void testInstallazioneApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {

		System.out.println(".......testInstallazioneApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		// installazione della app nello smartphone
		smartphoneServiceInstance.aggiungiApp(appInstance, smartphoneInstance);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testInstallazioneApp FAILED: installazione non avvenuta correttamente.");

		System.out.println(".......testInstallazioneApp fine: PASSED.............");

	}

	private static void testDisinstallazioneApp(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {

		System.out.println(".......testDisinstallazioneApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp fallito: smartphone non inserito. ");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp FAILED: app non inserita! ");

		// installazione della app nello smartphone
		smartphoneServiceInstance.aggiungiApp(appInstance, smartphoneInstance);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: installazione non avvenuta correttamente.");

		// ora la disinstallo e verifico se effettivamente e' stata disinstallata
		appServiceInstance.rimuoviAppDallaTabellaDiJoin(appInstance.getId());

		// lo ricarico eager
		smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		// verifico
		if (!smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: disinstallazione non avvenuta correttamente.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testDisinstallazioneApp fine: PASSED.............");

	}

	private static void testRimozioneSmartphoneAssociatoADueApp(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {

		System.out.println(".......testRimozioneSmartphoneAssociatoADueApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp fallito: smartphone non inserito. ");

		// creo due app e le inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp FAILED: app non inserita! ");

		App appInstance1 = new App("Whatsapp", dataInstallazione, dataAggiornamento, "2.1.1");
		appServiceInstance.inserisciNuovo(appInstance1);

		// verifica corretto inserimento
		if (appInstance1.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp FAILED: app non inserita! ");

		// installazione delle app nello smartphone
		smartphoneServiceInstance.aggiungiApp(appInstance, smartphoneInstance);
		smartphoneServiceInstance.aggiungiApp(appInstance1, smartphoneInstance);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException(
					"testRimozioneSmartphoneAssociatoADueApp FAILED: installazione non avvenuta correttamente.");

		// rimozione smartphone e verifica rimozione
		smartphoneServiceInstance.rimuoviSmartphoneDallaTabellaDiJoin(smartphoneInstance.getId());

		App appReloaded = appServiceInstance.caricaSingoloElementoEagerSmartphones(appInstance.getId());

		if (!appReloaded.getSmartphones().isEmpty())
			throw new RuntimeException(
					"testRimozioneSmartphoneAssociatoADueApp FAILED: si e'verificato un errore durante la rimozione dello smartphone.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());
		appServiceInstance.rimuovi(appInstance1.getId());

		System.out.println(".......testRimozioneSmartphoneAssociatoADueApp fine: PASSED.............");

	}

}
