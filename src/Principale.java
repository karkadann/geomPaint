import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Classe Principale servant a afficher la fenetre en respectant le modele MVC
 * @author Groupe 2
 * @version 1
 */
public class Principale {
	
	/**
     * Attribut nous permettant d'utiliser un log du 
     * message d'erreur pour la Fenetre
     */
    private static final Logger LOGGER = 
        Logger.getLogger(Principale.class.getName());
	
	/**
	 * Main du programme se deroule ainsi
	 * On initialise l'UI
	 * On creer le conteneur et on definit sa taille
	 * On creer et definit la fenetre
	 * @param args les arguments pouvant etre recuperer
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch (Exception e) {
			LOGGER.log(Level.INFO, "Erreur lors de la recuperation de l'UI", e);
		}
		
		Conteneur conteneur = new Conteneur();
		conteneur.setPreferredSize(new Dimension(1500,800));
		
		JFrame fenetre=new JFrame("GeomPaint :D");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setContentPane(conteneur); 
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null); // permet de centrer la fenetre au demarrage
	}
}
