import java.util.concurrent.RecursiveAction;

/**
 * Classe che visita l'albero binario in modo ricorsivo e parallelo e calcola il
 * risultato dell'operazione rappresentata da esso
 * 
 * @author giusf
 *
 */
public class VisitaAlbero extends RecursiveAction {
	// variabile per contare il numero di threads
	private static int contatoreThread;
	// variabile per salvare il nodo su cui operare
	private NodoAlbero nodo;
	// variabile per risultato totale
	private String risultato;
	// variabili per salvare i risultati parziali (ramo sinistro e ramo destro)
	private String risultatoDx, risultatoSx;
	// variabili per la ricorsione (visitare ricorsivamente il ramo sinistro e
	// quello destro)
	private VisitaAlbero destro, sinistro;

	/**
	 * Costruttore
	 * 
	 * @param nodo
	 *            il nodo su cui operare
	 */
	public VisitaAlbero(NodoAlbero nodo) {
		// controllo se il nodo è null o no
		if (nodo == null) {
			// se null il risultato viene impostato a 0
			risultato = "0";
			return;
		} else {
			// sennò assegno il parametro nodo alla variabile nodo
			this.nodo = nodo;
		}
		// incremento il numero di thread di 1 ogni volta che costruisco un
		// nuovo "thread"
		contatoreThread++;
	}

	/**
	 * Metodo per verificare che il nodo contenga un operatore
	 * 
	 * @param nodo
	 *            il nodo da controllare
	 * @return true se contiene un operatore, false se contiene un operando
	 */
	private static boolean isOperatore(NodoAlbero nodo) {
		if (nodo.getValore().equals("*") || nodo.getValore().equals("/") || nodo.getValore().equals("+") || nodo.getValore().equals("-"))
			return true;
		else
			return false;
	}

	/**
	 * Metodo per capire che operazione fare
	 * 
	 * @param nodo
	 *            il nodo da analizzare
	 * @return il valore dell'operatore se esiste, sennò null
	 */
	private static String getOperazione(NodoAlbero nodo) {
		if (isOperatore(nodo))
			return nodo.getValore();
		else
			return null;
	}

	// Metodo chiamato automaticamente quando un ForkJoinPool chiama il metodo
	// invoke su un oggetto di questa classe (che estende RecursiveAction)
	public void compute() {
		//se il nodo è = a null il risultato è impostato a 0
		if (this.nodo == null) {
			risultato = "0";
			return;
		} else {
			//sennò creo ricorsivamente un nuovo oggetto passandogli prima il figlio destro e poi quello sinistro
			destro = new VisitaAlbero(this.nodo.getFiglioDestro());
			sinistro = new VisitaAlbero(this.nodo.getFiglioSinistro());

			//partono in parallelo i due rami
			invokeAll(destro, sinistro);

			//alla fine delle operazioni assegno i risultati parziali con il metodo getRisultato()
			risultatoDx = destro.getRisultato();
			risultatoSx = sinistro.getRisultato();
		}
	}

	/**
	 * Metodo che restituisce il risultato 
	 * @return
	 */
	public String getRisultato() {
		//se il nodo è = a null restituisce 0
		if (nodo == null) {
			return "0";
		} else {
			// se è un nodo contenente un operatore
			if (isOperatore(nodo)) {
				// vedo che tipo di operazione fare
				switch (getOperazione(nodo)) {
				case "*":
					risultato = "" + (Double.parseDouble(risultatoDx) * Double.parseDouble(risultatoSx));
					return risultato;
				case "/":
					risultato = "" + (Double.parseDouble(risultatoSx) / Double.parseDouble(risultatoDx));
					return risultato;
				case "+":
					risultato = "" + (Double.parseDouble(risultatoDx) + Double.parseDouble(risultatoSx));
					return risultato;
				case "-":
					risultato = "" + (Double.parseDouble(risultatoSx) - Double.parseDouble(risultatoDx));
					return risultato;
				default:
					break;
				}
			} else {
				//se non è un operatore restituisco direttamente il valore del nodo
				risultato = nodo.getValore();
			}
			return this.risultato;
		}
	}

	/**
	 * Metodo che restituisce il numero di thread totali
	 * @return
	 */
	public int getThreads() {
		return contatoreThread;
	}
}
