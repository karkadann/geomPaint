import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * Classe qui sert de controleur graphique pour les rectangle
 * @author Groupe 2
 * @version 1
 */

public class ControleurFigure extends MouseInputAdapter {
	
	boolean edition, deplacement ;
	private boolean dessiner;
	private boolean rectangleOn ;
	private boolean cercleOn;
	private boolean triangleOn;
	private boolean traitOn;
	int index ;
	Point p1, p2, p3, pointEditer ;	
	FigureGeom figure ;
	ListFigures lsFigures;
	
	/**
	 * Constructeur de ControleurRectangle
	 * @param trs
	 */
	
	public ControleurFigure(ListFigures trs) {
		lsFigures = trs ;	
		setDessiner(true) ;
	}

	public void mouseDragged(MouseEvent e){		
		if (edition && !isDessiner()) {
			if (figure instanceof UnRectangle) {
				UnRectangle rectangle = (UnRectangle) lsFigures.getFigures().get(index);
				rectangle.editerFigure2p(pointEditer, e.getPoint()) ;
				lsFigures.setFigure(index, rectangle);
			}
			if (figure instanceof UnTrait) {
				UnTrait trait = (UnTrait) lsFigures.getFigures().get(index);
				trait.editerFigure2p(pointEditer, e.getPoint()) ;
				lsFigures.setFigure(index, trait);
			}
			if (figure instanceof UnCercle) {
				UnCercle cercle = (UnCercle) lsFigures.getFigures().get(index);
				cercle.editerFigure2p(pointEditer, e.getPoint()) ;
				lsFigures.setFigure(index, cercle);
			}				
			if (figure instanceof UnTriangle) {	
				UnTriangle triangle = (UnTriangle) lsFigures.getFigures().get(index);
				triangle.editerTriangle(pointEditer, e.getPoint()) ;
				lsFigures.setFigure(index, triangle);
			}
			pointEditer = e.getPoint() ;			
		}			
		else if (deplacement && !isDessiner()) {								
				if (figure instanceof UnRectangle) {
					UnRectangle rectangle = (UnRectangle) lsFigures.getFigures().get(index);
					figure.deplacerFigure2p(pointEditer, e.getPoint()) ;
					lsFigures.setFigure(index, rectangle);
				}
				if (figure instanceof UnTrait) {
					UnTrait trait = (UnTrait) lsFigures.getFigures().get(index);
					trait.deplacerFigure2p(pointEditer, e.getPoint()) ;
					lsFigures.setFigure(index, trait);
				}
				if (figure instanceof UnCercle) {
					UnCercle rectangle = (UnCercle) lsFigures.getFigures().get(index);
					figure.deplacerFigure2p(pointEditer, e.getPoint()) ;
					lsFigures.setFigure(index, rectangle);
				}				
				if (figure instanceof UnTriangle) {	
					UnTriangle triangle = (UnTriangle) lsFigures.getFigures().get(index);
					triangle.deplacerTriangle(pointEditer, e.getPoint()) ;
					lsFigures.setFigure(index, triangle);
				}
			pointEditer = e.getPoint() ;					
		}
	}
	
	public void mousePressed(MouseEvent e){
			if (indexEditer(e.getPoint()) != -1 && ! isDessiner()){
				edition = true ;
				index = indexEditer(e.getPoint());
				pointEditer = e.getPoint() ;
				figure = lsFigures.getFigures().get(index);
			}		
			else if (indexDeplacer(e.getPoint()) != -1 && ! isDessiner()){
				deplacement = true ;			
				index = indexDeplacer(e.getPoint());
				pointEditer = e.getPoint() ;
				figure = lsFigures.getFigures().get(index);				
			}
	}
	
	public void mouseReleased(MouseEvent e){
		edition = false ;
		deplacement = false;
		pointEditer = null;		
	}
	
	public void mouseClicked(MouseEvent e) {
		if (isDessiner())  {			
			if (p1 == null) 
				p1 = new Point (e.getPoint()) ;		
			else if (p2 == null) {
				p2 = new Point (e.getPoint()) ;											
				if (isRectangleOn()) {       			
					UnRectangle rt = new UnRectangle(p1,p2);
					lsFigures.addFigure(rt);
					p1 = null ;
					p2 = null ;
				}
				else if (isCercleOn()) {       				
					UnCercle cl = new UnCercle(p1,p2);
					lsFigures.addFigure(cl);
					p1 = null ;
					p2 = null ;
				}
				else if (isTraitOn()) {     			
					UnTrait tr = new UnTrait(p1,p2);
					lsFigures.addFigure(tr);	
					p1 = null ;
					p2 = null ;
				}									
			}	
			else if (isTriangleOn() && p3 == null) {								
				p3 = new Point (e.getPoint()) ;
				UnTriangle triangle = new UnTriangle(p1,p2,p3);
				lsFigures.addFigure(triangle);	
				p1 = null ;
				p2 = null ;
				p3 = null ;
			}	
		}
	}
		
	public int indexEditer (Point p){
		int index = -1 ;
		for (int i = 0; i < lsFigures.getFigures().size(); i++) {
			figure = lsFigures.getFigures().get(i);
			if ( figure instanceof UnRectangle || figure instanceof UnCercle || figure instanceof UnTrait)
				if (figure.tolerance(p))
					 index = i ;						
			if (figure instanceof UnTriangle)
				if (((UnTriangle)figure).toleranceTriangle(p))
					 index = i ;
		}
		return index ;
	}
	
	public int indexDeplacer (Point p){
		int index = -1 ;
		for (int i = 0; i < lsFigures.getFigures().size(); i++) {
			figure = lsFigures.getFigures().get(i);	
			if ( figure instanceof UnRectangle)
				if (((UnRectangle)figure).insideRectangle(p))
					index = i ;	
			if ( figure instanceof UnTriangle)
				if (((UnTriangle)figure).isInsideTriangle(p))
					index = i ;	
			if ( figure instanceof UnCercle)
				if (((UnCercle)figure).insideCercle(p))
					index = i ;	
			if ( figure instanceof UnTrait)
				if (((UnTrait)figure).insideTrait(p))
					index = i ;	
		}
		return index ;
	}	
	
	public void toggleMode (boolean b){
		for (int i = 0; i < lsFigures.getFigures().size(); i++) {			
			lsFigures.getFigures().get(i).setSelectOn(b);
			lsFigures.setFigure(i,lsFigures.getFigures().get(i));					
		}
		p1 = null ;
		p2 = null ;
		p3 = null ;
	}

	/**
	 * @return the rectangleOn
	 */
	public boolean isRectangleOn() {
		return rectangleOn;
	}

	/**
	 * @param rectangleOn the rectangleOn to set
	 */
	public void setRectangleOn(boolean rectangleOn) {
		this.rectangleOn = rectangleOn;
	}

	/**
	 * @return the traitOn
	 */
	public boolean isTraitOn() {
		return traitOn;
	}

	/**
	 * @param traitOn the traitOn to set
	 */
	public void setTraitOn(boolean traitOn) {
		this.traitOn = traitOn;
	}

	/**
	 * @return the triangleOn
	 */
	public boolean isTriangleOn() {
		return triangleOn;
	}

	/**
	 * @param triangleOn the triangleOn to set
	 */
	public void setTriangleOn(boolean triangleOn) {
		this.triangleOn = triangleOn;
	}

	/**
	 * @return the cercleOn
	 */
	public boolean isCercleOn() {
		return cercleOn;
	}

	/**
	 * @param cercleOn the cercleOn to set
	 */
	public void setCercleOn(boolean cercleOn) {
		this.cercleOn = cercleOn;
	}

	/**
	 * @return the dessiner
	 */
	public boolean isDessiner() {
		return dessiner;
	}

	/**
	 * @param dessiner the dessiner to set
	 */
	public void setDessiner(boolean dessiner) {
		this.dessiner = dessiner;
	}
	/**
	 * methode qui �fface les point lors du clique sur un bouton
	 */
	public void effacerPoints() {
		if (p1 != null) p1 = null;
		if (p2 != null) p2 = null;
		if (p3 != null) p3 = null;
	}
}