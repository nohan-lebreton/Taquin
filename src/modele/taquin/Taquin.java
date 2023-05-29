package taquin;
import  tuile.Tuile;
import  tuile.TuileMovable;
import  java.util.Random;
import  java.util.Scanner;
import  java.util.ArrayList;
import  modele.AbstractListenableModel;

public class Taquin extends AbstractListenableModel{

    private int lignes;
    private int colonnes;
    private Tuile[][] plateau;
    private Tuile[][] plateauInit;
    private Tuile caseVide;
    private int nbMouvementAleatoire;

    /**
        * Le constructeur permet d'instancier un taquin
        * @param lignes correspond aux nombre de lignes qu'on souhaite avoir dans le taquin
        * @param colonnes correspond aux nombre de colonnes qu'on souhaite avoir dans le taquin 
    */
    public Taquin(int lignes, int colonnes){
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.plateau = this.creerPlateau();
        this.plateauInit = this.copiePlateau();
        this.caseVide = this.getTuileIdentifiant(0);
        this.nbMouvementAleatoire = 7;
    }

    /**
        * Cette méthode génère les coordonnées de la case vide
        * @param tailleLigne correspond à la dimension en ligne
        * @param tailleColonne correspond à la dimension en colonne
        * @return un tableau d'entier contenant les coordonnées de la case vide
    */
    private int[] randomNumberForEmptyCase(int tailleLigne, int tailleColonne){
        Random seed = new Random();
        int[] tabCoordonneesCaseVide = new int[2];
        tabCoordonneesCaseVide[0] = seed.nextInt(tailleLigne);
        tabCoordonneesCaseVide[1] = seed.nextInt(tailleColonne);
        return tabCoordonneesCaseVide;
    }

    /**
        * Cette méthode permet de créer le plateau de jeu
        * @return un tableau de tuilles
    */
    private Tuile[][] creerPlateau(){
        Tuile[][] tabTuiles = new Tuile[this.lignes][this.colonnes];

        // La case vide a pour identifiant 0
        int[] CoordonneesCaseVide = this.randomNumberForEmptyCase(tabTuiles.length, tabTuiles[0].length);
        tabTuiles[CoordonneesCaseVide[0]][CoordonneesCaseVide[1]] = new Tuile(CoordonneesCaseVide[0],CoordonneesCaseVide[1], 0);
        
        int identifiant = 1;
        for(int ligne = 0; ligne < this.lignes; ligne++){
            for(int colonne = 0; colonne < this.colonnes; colonne++){
                if (!(ligne == CoordonneesCaseVide[0] && colonne == CoordonneesCaseVide[1])){
                    tabTuiles[ligne][colonne] = new Tuile(ligne,colonne, identifiant);
                    identifiant +=1;
                }
            }
        }
        return tabTuiles;
    }

    /**
        * Cet accesseur permet de retourner le plateau courant, il va changer au cours de la partie
        * @return le plateau de tuiles présent dans le taquin
    */
    public Tuile[][] getBoard(){
        return this.plateau;
    }

    /**
        * Cet accesseur permet de retourner le plateau initial 
        * @return le plateau de tuiles initial
    */
    public Tuile[][] getBoardInit(){
        return this.plateauInit;
    }

    /**
        * Cette méthode permet d'échanger les tuiles
        * @param tuile correspond à la tuile que l'on souhaite changer
    */
    public void changeTuile(Tuile tuile){
        // On sauvegarde les coordonnées de la case vide
        int coordCaseVide_x = this.caseVide.getPos_x();
        int coordCaseVide_y = this.caseVide.getPos_y();

        // On intervertit les deux tuiles
        this.plateau[this.caseVide.getPos_x()][this.caseVide.getPos_y()] = tuile;
        this.plateau[tuile.getPos_x()][tuile.getPos_y()] = this.caseVide;

        // On modifie les coordonnées par celles de la tuile qu'on souhaite déplacer
        this.caseVide.setPos_x(tuile.getPos_x());
        this.caseVide.setPos_y(tuile.getPos_y());

        // Pareil pour la tuile
        tuile.setPos_x(coordCaseVide_x);
        tuile.setPos_y(coordCaseVide_y);        
    }

    /**
        * Cet accesseur permet de retourner le nombre de ligne du taquin
        * @return un entier correspond au nombre de ligne
    */
    public int getLignes(){
        return this.lignes;
    }

    /**
        * Cet accesseur permet de retourner le nombre de colonnes du taquin
        * @return un entier correspond au nombre de colonne
    */
    public int getColonnes(){
        return this.colonnes;
    }

    /** 
        * Cet accesseur permet d'obtenir une tuile
        * @param pos_x correspond à la position en x
        * @param pos_y correspond à la position en y
        * @return la tuile selon les coordonées
    */
    public Tuile getTuile(int pos_x, int pos_y){
        return this.plateau[pos_x][pos_y];
    }

    /** 
        * Cet accesseur permet d'obtenir une tuile grâce à son identifiant
        * @param identifiant correspond à l'identifiant de la tuile
        * @return la tuile selon l'identifiant
    */
    public Tuile getTuileIdentifiant(int identifiant){
        for(int ligne = 0; ligne < this.lignes; ligne++){
            for(int colonne = 0; colonne < this.colonnes; colonne++){
                if (this.plateau[ligne][colonne].getIdentifiant() == identifiant){
                    return this.plateau[ligne][colonne];
                }
            }
        }
        return null;
    }

    /** 
        * Cette méthode permet de copier le plateau courant
        * @return la copie du plateau
    */
    private Tuile[][] copiePlateau(){
        Tuile[][] newPlateau = new Tuile[this.lignes][this.colonnes];
        for(int ligne = 0; ligne < this.lignes; ligne++){
            for(int colonne = 0; colonne < this.colonnes; colonne++){
                newPlateau[ligne][colonne] = this.plateau[ligne][colonne];
            }
        }
        return newPlateau;
    }

    /** 
        * Cette méthode permet de terminer la partie
        * @param plateauInit prend le plateau initial
        * @param plateauFinal prend le plateau final
        * @return un booleen, true si la partie est terminé, false sinon.
    */
    public boolean isOver(Tuile[][] plateauInit, Tuile[][] plateauFinal){
        for (int ligne = 0; ligne < this.lignes; ligne++){
            for (int colonne = 0; colonne < this.colonnes; colonne++){
                if (plateauInit[ligne][colonne].equals(plateauFinal[ligne][colonne]) == false){
                    return false;
                }
            }
        }
        return true;
    }

    /** 
        * Cette méthode permet d'avertir les écouteurs que l'état à changé.
    */
    public void etatSuivant(){
        this.deplacementTuile();
    }

    /** 
        * Cette méthode permet de déplacer les tuiles du plateau de façon stochastique
    */
    public void deranger(){
        Random aleatoire = new Random();

        for (int x = 0; x < this.nbMouvementAleatoire; x++ ){

            // On créé une liste de TuileMovable -> Tuile qu'on peut déplacer (True)
            // Une TuileMovable est composée -> Tuile
            //                               -> entier qui représente l'action
            ArrayList<TuileMovable> tabTuileMovable = new ArrayList<>();

            for(int ligne = 0; ligne < this.lignes; ligne++){
                for(int colonne = 0; colonne < this.colonnes; colonne++){
                    if (!(this.plateau[ligne][colonne].tuileMovable(this) == null)){
                        tabTuileMovable.add(this.plateau[ligne][colonne].tuileMovable(this));
                    }
                }
            }

            // On récupère aléatoirement une TuileMovable au sein de la liste
            TuileMovable tuileMovableAleatoire = tabTuileMovable.get(aleatoire.nextInt(tabTuileMovable.size()));

            // On récupère la tuile de la TuileMovable précédemment récupéré
            Tuile tuileCible = tuileMovableAleatoire.getTuile();
            // On récupère l'entier qui représente l'action
            int move = tuileMovableAleatoire.getNumeroAction();

            // Choix de l'action en fonction de l'entier assigné à la TuileMovable
            if(move == 1){
                tuileCible.haut(this);
            }
            else if(move == 2){
                tuileCible.bas(this);
            }
            else if(move == 3){
                tuileCible.gauche(this);
            }
            else if(move == 4){
                tuileCible.droite(this);
            }

        }
        this.etatSuivant();
    }

    /** 
        * Cet accesseur permet d'obtenir la tuile vide
        * @return la tuile vide
    */
    public Tuile getCaseVide(){
        return this.caseVide;
    }

    /** 
        * Cette méthode DEBUG permet de connaître les tuiles qui sont déplaçable
    */
    public void DEBUGDeplacement(){
        // Création d'un tableau de bool, les cases adjacentes à la case vide sont déplaçable (True) sinon (False)
        boolean[][] tabBool = new boolean[this.lignes][this.colonnes];

        for(int ligne = 0; ligne < this.lignes; ligne++){
            for(int colonne = 0; colonne < this.colonnes; colonne++){
                if (!(this.plateau[ligne][colonne] == this.caseVide)){
                    tabBool[ligne][colonne] = this.plateau[ligne][colonne].DEBUGtuileMovable(this);
                }
            }
        }
        // Affichage
        for(int ligne = 0; ligne < this.lignes; ligne++){
            for(int colonne = 0; colonne < this.colonnes; colonne++){
                System.out.print(""+tabBool[ligne][colonne] + " ");
            }
            System.out.println();

        }
    }
}
