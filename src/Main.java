import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Classe principale
 * @author giusf
 *
 */
public class Main {
	// oggetto per leggere da file
	static LettoreFile lettore;
	// lista per immagazzinare i vari caratteri letti
	static ArrayList<String> lista;

	/**
	 * Metodo che:
	 * 1 - legge da file e crea la lista di caratteri letti
	 * 2 - Crea un albero binario a partire dalla lista creata in precedenza
	 * 3 - Svolge le operazioni dell'albero in modo sequenziale
	 * 	   (scrivendo risultato e tempo impiegato) 
	 * 4 - Svolge le operazioni dell'albero in modo parallelo
	 * 	   (scrivendo risultato, tempo impiegato, numero di thread impiegati e numero di processori) 
	 * 5 - Scrive il tempo totale impiegato
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		//variabile per calcolare tempo totale
		long startProgramma = System.currentTimeMillis();
		//variabile per immagazzinare il numero di processori
		int processori = Runtime.getRuntime().availableProcessors();
		//inizializzo il lettore
		lettore = new LettoreFile("expression.txt");
		//inizializzo la lista
		lista = new ArrayList<String>();
		//messaggio per l'utente (ci sarà attesa)
		System.out.println("Lettura file e creazione lista in corso...");
		//variabile per calcolare il tempo impiegato per leggere il file
		//e creare la lista
		long startLettura = System.currentTimeMillis();
		//finchè ci sono caratteri nel file aggiungi il carattere letto alla lista
		while (lettore.hasNext())
			lista.add(lettore.nextElement());
		//alla fine della lettura chiudi lo stream dello scanner
		lettore.chiudiStream();
		//fine tempo di lettura/creazione lista
		long endLettura = System.currentTimeMillis();
		//messaggio per l'utente
		System.out.println("Lettura file e lista completate in " + (endLettura - startLettura) + " ms");

		//messaggio per l'utente (ci sarà attesa)
		System.out.println("\nCreazione albero in corso...");
		//variabile per tempo di creazione albero
		long startAlbero = System.currentTimeMillis();
		//creo la radice dell'albero
		NodoAlbero albero = new NodoAlbero(lista.get(0));
		//aggiungo ogni carattere della lista come nuovo nodo dell'albero
		for (int i = 1; i < lista.size(); i++)
			albero.inserisciNodo(albero, lista.get(i));
		//fine tempo creazione albero
		long endAlbero = System.currentTimeMillis();
		//messaggio per l'utente
		System.out.println("Albero completato in " + (endAlbero - startAlbero) + " ms");

		//messaggio per l'utente (ci sarà attesa)
		System.out.println("\nOperazioni senza Thread in corso...");
		//variabile per tempo operazioni sequenziali
		long startOperazioni = System.currentTimeMillis();
		//variabile per immagazzinare il risultato delle operazioni
		//(il metodo visitaPostOrdine() verrà richiamato ricorsivamente)
		String risultato = albero.visitaPostOrdine(albero);
		//fine tempo operazioni sequenziali
		long endOperazioni = System.currentTimeMillis();
		//messaggi per l'utente
		System.out.println("Operazione senza Thread completata in " + (endOperazioni - startOperazioni) + " ms");
		System.out.println("Risultato senza Thread \t= " + risultato);

		//creo un oggetto di tipo VisitaAlbero passandogli la radice dell'albero
		VisitaAlbero visita = new VisitaAlbero(albero);
		//creo un ForkJoinPool dandogli come parametro il numero di processori
		//(in questo modo eseguirà al massimo 8 thread per volta)
		ForkJoinPool pool = new ForkJoinPool(processori);
		//messaggio per l'utente (ci sarà attesa)
		System.out.println("\nOperazioni con Thread in corso...");
		//variabile per tempo operazioni parallele
		long startOpThread = System.currentTimeMillis();
		//invoco compute() chiamando invoke()
		pool.invoke(visita);
		//immagazzino il risultato
		String risultatoThread = visita.getRisultato();
		//immagazzino il numero totale di thread
		int numeroThread = visita.getThreads();
		//fine tempo operazioni parallele
		long endOpThread = System.currentTimeMillis();
		//messaggi per l'utente
		System.out.println("Operazione con Thread completata in " + (endOpThread - startOpThread) + " ms con "
				+ numeroThread + " threads eseguiti da " + processori + " processori.");
		System.out.println("Risultato con Thread \t= " + risultatoThread);
		
		//fine tempo totale
		long endProgramma = System.currentTimeMillis();
		//messaggio per l'utente
		System.out.println("\nTempo esecuzione totale = " + (endProgramma - startProgramma) + " ms");

	}
}
