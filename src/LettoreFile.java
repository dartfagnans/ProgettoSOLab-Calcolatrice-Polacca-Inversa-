import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe che legge un carattere alla volta da un file di testo
 * @author giusf
 *
 */
public class LettoreFile {
	//variabile per immagazzinare il file da leggere
	private File file;
	//scanner per leggere da file
	private Scanner lettore;
	
	/**
	 * Costruttore che inizializza il file e lo scanner
	 * @param filePath
	 */
	public LettoreFile (String filePath) {
		this.file = new File (filePath);
		try {
			this.lettore = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File NON trovato!");
			System.exit(-1);
		}
	}
	
	/**
	 * Chiude lo stream dello scanner
	 */
	public void chiudiStream () {
		lettore.close();
	}
	
	/**
	 * Restituisce il prossimo carattere del file
	 * @return
	 */
	public String nextElement () {
		return this.lettore.next();
	}
	
	/**
	 * Restituisce true se ci sono altri caratteri da leggere
	 * @return
	 */
	public boolean hasNext () {
		return lettore.hasNext();
	}
}
