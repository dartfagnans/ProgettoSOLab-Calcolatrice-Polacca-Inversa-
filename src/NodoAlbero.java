/**
 * Classe che serve per rappresentare un albero binario:
 * ogni nodo rappresenta un operatore o un operando, con questi ultimi che
 * rappresentano le foglie dell'albero
 * @author giusf
 *
 */
public class NodoAlbero {
	//il valore del nodo è una stringa
	private String valore;
	
	//ogni nodo ha un figlio sinistro, un figlio destro e un nodo padre
	private NodoAlbero figlioSinistro;
	private NodoAlbero figlioDestro;
	private NodoAlbero nodoPadre;
	
	//variabile di controllo per verificare che l'ultimo letto sia un operatore o un operando
	private boolean isUltimoLettoOperatore = false;
	
	//variabile per memorizzare l'ultimo nodo inserito nell'albero
	private NodoAlbero ultimoNodoInserito = null;

	/**
	 * Costruttore che crea un nodo a sè stante (senza figli nè padre).
	 * @param carattere valore del nodo
	 */
	public NodoAlbero(String carattere) {
		this.valore = carattere;
		this.figlioSinistro = null;
		this.figlioDestro = null;
		this.nodoPadre = null;
	}

	/**
	 * Metodo per inserire un nodo nell'albero
	 * @param radice la radice dell'albero
	 * @param carattere valore del nodo
	 */
	public void inserisciNodo(NodoAlbero radice, String carattere) {
		NodoAlbero nuovoNodo = new NodoAlbero(carattere);

		// verifico che l'albero non sia vuoto (se vuoto il nuovo nodo diventa
		// la radice)
		if (ultimoNodoInserito == null) {
			ultimoNodoInserito = radice;
			isUltimoLettoOperatore = true;
		}

		// controllo se l'ultimo elemento inserito è un operatore
		if (isUltimoLettoOperatore) {
			// inserisco il nuovoNodo come figlio sinistro
			// dell'ultimoNodoInserito
			ultimoNodoInserito.figlioSinistro = nuovoNodo;
			nuovoNodo.nodoPadre = ultimoNodoInserito;
		} else {
			// torno indietro finchè non trovo un nodo con figlio destro = null
			// parto dal nodo padre dell'ultimo nodo inserito
//			Albero nodoTemp = ultimoNodoInserito;
			NodoAlbero nodoCorrente = ultimoNodoInserito.nodoPadre;
			while (nodoCorrente != null) {
				if (nodoCorrente.figlioDestro == null) {
					// quando lo trovo inserisco il nuovo nodo come figlio
					// destro
					nodoCorrente.figlioDestro = nuovoNodo;
					nuovoNodo.nodoPadre = nodoCorrente;
					break;
				} else {
					nodoCorrente = nodoCorrente.nodoPadre;
				}
			}
		}

		// aggiorno ultimoNodoInserito e isUltimoLettoOperatore
		ultimoNodoInserito = nuovoNodo;
		if (isOperatore(nuovoNodo)) {
			isUltimoLettoOperatore = true;
		} else {
			isUltimoLettoOperatore = false;
		}
	}
	
	/**
	 * Metodo per ritornare il valore dell'operazione (visita in post ordine)
	 * @param nodo La radice dell'albero
	 * @return il risultato finale dell'operazione su tutto l'albero
	 */
	public static String visitaPostOrdine (NodoAlbero nodo) {
		//se nodo = null ritorno 0
		if (nodo == null) {
			return "0";
		}
		
		//visita in post ordine (visito prima il ramo destro e poi quello sinistro)
		String risultatoDestro = visitaPostOrdine (nodo.figlioDestro);
		String risultatoSinistro = visitaPostOrdine (nodo.figlioSinistro);
		
		//se è un nodo contenente un operatore
		if (isOperatore(nodo)) {
			//vedo che tipo di operazione fare
			switch (getOperazione(nodo)) {
			case "*":
				return ""+(Double.parseDouble(risultatoDestro) * Double.parseDouble(risultatoSinistro));
			case "/":
				return ""+(Double.parseDouble(risultatoSinistro) / Double.parseDouble(risultatoDestro));
			case "+":
				return ""+(Double.parseDouble(risultatoDestro) + Double.parseDouble(risultatoSinistro));
			case "-":
				return ""+(Double.parseDouble(risultatoSinistro) - Double.parseDouble(risultatoDestro));
			default:
				break;
			}
		}
		//se il nodo contiene un operando ritorno direttamente il valore
		return nodo.valore;
	}
	
	/**
	 * Metodo per verificare che il nodo contenga un operatore
	 * @param nodo il nodo da controllare
	 * @return true se contiene un operatore, false se contiene un operando
	 */
	private static boolean isOperatore (NodoAlbero nodo) {
		if (nodo.valore.equals("*") || nodo.valore.equals("/")
				|| nodo.valore.equals("+") || nodo.valore.equals("-"))
			return true;
		else return false;
	}
	
	/**
	 * Metodo per capire che operazione fare
	 * @param nodo il nodo da analizzare
	 * @return il valore dell'operatore se esiste, sennò null
	 */
	private static String getOperazione (NodoAlbero nodo) {
		if (isOperatore(nodo)) return nodo.valore;
		else return null;
	}
	
	public String toString () {
		return (String) this.valore;
	}
	
	//SETTERS E GETTERS
	
	public NodoAlbero getFiglioSinistro() {
		return figlioSinistro;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public void setFiglioSinistro(NodoAlbero figlioSinistro) {
		this.figlioSinistro = figlioSinistro;
	}

	public NodoAlbero getFiglioDestro() {
		return figlioDestro;
	}

	public void setFiglioDestro(NodoAlbero figlioDestro) {
		this.figlioDestro = figlioDestro;
	}

	public NodoAlbero getNodoPadre() {
		return nodoPadre;
	}

	public void setNodoPadre(NodoAlbero nodoPadre) {
		this.nodoPadre = nodoPadre;
	}
}
