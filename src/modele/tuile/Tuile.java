package tuile;

import taquin.Taquin;
import java.awt.image.BufferedImage;


public class Tuile{
    private int pos_x;
    private int pos_y;
    private int identifiant;
    private BufferedImage image;

    /**
        * Le constructeur permet d'instancier une tuile
        * @param pos_x correspond à la position x de la tuile
        * @param pos_y correspond à la position y de la tuile
        * @param identifiant correspond à l'identifiant de la tuile
    */
    public Tuile(int pos_x, int pos_y, int identifiant){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.identifiant = identifiant;
        this.image = null;
    }

    /** 
        * Cet accesseur permet d'obtenir la position x de la tuile
        * @return la position x
    */
    public int getPos_x(){
        return this.pos_x;
    }

    /** 
        * Cet accesseur permet d'obtenir la position y de la tuile
        * @return la position y
    */
    public int getPos_y(){
        return this.pos_y;
    }

    /** 
        * Cet accesseur permet de modifier la position x de la tuile
    */
    public void setPos_x(int newPos_x){
        this.pos_x = newPos_x;
    }

    /** 
        * Cet accesseur permet de modifier la position y de la tuile
    */
    public void setPos_y(int newPos_y){
        this.pos_y = newPos_y;
    }

    /** 
        * Cet accesseur permet d'obtenir l'identifiant de la tuile
        * @return l'identifiant
    */
    public int getIdentifiant(){
        return this.identifiant;
    }

    /** 
        * Cet accesseur permet d'obtenir l'image de la tuile
        * @return l'image
    */
    public BufferedImage getImage(){
        return this.image;
    }

    /** 
        * Cet accesseur permet de modifier l'image de la tuile
    */
    public void setImage(BufferedImage newImage){
        this.image = newImage;
    }
    
    /** 
        * Cette méthode vérifie si la tuile à le droit de se déplacer vers le haut
        * @param taquin correspond au plateau courant
    */
    public void haut(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();
        if (caseVide.getPos_x() >= 0 && this.pos_x == caseVide.getPos_x()+1 && this.pos_x < taquin.getLignes() && this.pos_y == caseVide.getPos_y()){
            taquin.changeTuile(this);
        } 
    }

    /** 
        * Cette méthode vérifie si la tuile à le droit de se déplacer vers le bas
        * @param taquin correspond au plateau courant
    */
    public void bas(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();
        if (caseVide.getPos_x() > 0 && caseVide.getPos_x() < taquin.getLignes() && this.pos_x == caseVide.getPos_x()-1 && this.pos_y == caseVide.getPos_y()){
            taquin.changeTuile(this);
        }  
    }

    /** 
        * Cette méthode vérifie si la tuile à le droit de se déplacer vers le gauche
        * @param taquin correspond au plateau courant
    */
    public void gauche(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();
        if (caseVide.getPos_y() >= 0 && this.pos_y == caseVide.getPos_y()+1 && this.pos_y < taquin.getColonnes() && this.pos_x == caseVide.getPos_x()){
            taquin.changeTuile(this);
        }    
    }
  
    /** 
        * Cette méthode vérifie si la tuile à le droit de se déplacer vers le droite
        * @param taquin correspond au plateau courant
    */
    public void droite(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();
        if (caseVide.getPos_y() > 0 && caseVide.getPos_y() < taquin.getLignes() && this.pos_y == caseVide.getPos_y()-1 && this.pos_x == caseVide.getPos_x()){
            taquin.changeTuile(this);
        }      
    }

    /** 
        * Cette méthode vérifie si la tuile à le droit de se déplacer
        * @param taquin correspond au plateau courant
        * @return une TuileMovable ou null si ce n'est pas possible
    */
    public TuileMovable tuileMovable(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();

        // haut
        if (caseVide.getPos_x() >= 0 && this.pos_x == caseVide.getPos_x()+1 && this.pos_x < taquin.getLignes() && this.pos_y == caseVide.getPos_y()){
            //System.out.println("haut");
            return new TuileMovable(this,1);
        }
        // bas
        if (caseVide.getPos_x() > 0 && caseVide.getPos_x() < taquin.getLignes() && this.pos_x == caseVide.getPos_x()-1 && this.pos_y == caseVide.getPos_y()){
            //System.out.println("bas");
            return new TuileMovable(this,2);
        }
        // gauche
        if (caseVide.getPos_y() >= 0 && this.pos_y == caseVide.getPos_y()+1 && this.pos_y < taquin.getColonnes() && this.pos_x == caseVide.getPos_x()){
            //System.out.println("gauche");
            return new TuileMovable(this,3);
        }
        // droite
        if (caseVide.getPos_y() > 0 && caseVide.getPos_y() < taquin.getLignes() && this.pos_y == caseVide.getPos_y()-1 && this.pos_x == caseVide.getPos_x()){
            //System.out.println("droite");
            return new TuileMovable(this,4);
        }
        return null;
    }

    /** 
        * Cette méthode DEBUG vérifie si la tuile à le droit de se déplacer
        * @param taquin correspond au plateau courant
        * @return un booleen, true si la tuile est déplaçable, false sinon
    */
    public boolean DEBUGtuileMovable(Taquin taquin){
        Tuile caseVide = taquin.getCaseVide();

        // haut
        if (caseVide.getPos_x() >= 0 && this.pos_x == caseVide.getPos_x()+1 && this.pos_x < taquin.getLignes() && this.pos_y == caseVide.getPos_y()){
            //System.out.println("haut");
            return true;
        }
        // bas
        if (caseVide.getPos_x() > 0 && caseVide.getPos_x() < taquin.getLignes() && this.pos_x == caseVide.getPos_x()-1 && this.pos_y == caseVide.getPos_y()){
            //System.out.println("bas");
            return true;
        }
        // gauche
        if (caseVide.getPos_y() >= 0 && this.pos_y == caseVide.getPos_y()+1 && this.pos_y < taquin.getColonnes() && this.pos_x == caseVide.getPos_x()){
            //System.out.println("gauche");
            return true;
        }
        // droite
        if (caseVide.getPos_y() > 0 && caseVide.getPos_y() < taquin.getLignes() && this.pos_y == caseVide.getPos_y()-1 && this.pos_x == caseVide.getPos_x()){
            //System.out.println("droite");
            return true;
        }
        return false;
        
    }
    
    
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Tuile) && ((Tuile)obj).getPos_x() == this.pos_x && ((Tuile)obj).getPos_y() == this.pos_y && ((Tuile)obj).getIdentifiant() == this.identifiant;
    }

    @Override
    public int hashCode(){
        return (this.pos_x + this.pos_y + this.identifiant);
    }

    
    @Override
    public String toString(){
        return "" + this.identifiant ;
    }
}


