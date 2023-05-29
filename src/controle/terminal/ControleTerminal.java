package terminal;

import taquin.Taquin;
import tuile.Tuile;
import java.util.Scanner;
import java.util.ArrayList;
import tuile.TuileMovable;


public class ControleTerminal{
    private Taquin taquin;
    private int tailleTaquin;

    /**
        * Le constructeur permet d'instancier les contrôles console du jeu
        * @param taquin correspond au taquin courant
    */
    public ControleTerminal(Taquin taquin){
        this.taquin = taquin;
        this.tailleTaquin = this.taquin.getLignes() * this.taquin.getColonnes();
        this.init();
    }

    /** 
        * Cette méthode permet de lancer les contrôles console du jeu, on demande à l'utilisateur quelle tuile qu'il souhaite déplacer
    */
    public void init(){

        System.out.println("Le taquin ci-dessus à été dérangé !!! \n");

        while (!(this.taquin.isOver(this.taquin.getBoardInit(),this.taquin.getBoard()))){

            // On créé une liste de TuileMovable -> Tuile qu'on peut déplacer (True)
            // Une TuileMovable est composée -> Tuile
            //                               -> entier qui représente l'action
            ArrayList<TuileMovable> tabTuileMovable = new ArrayList<>();

            for(int ligne = 0; ligne < this.taquin.getLignes(); ligne++){
                for(int colonne = 0; colonne < this.taquin.getColonnes(); colonne++){
                    if (!(this.taquin.getBoard()[ligne][colonne].tuileMovable(this.taquin) == null)){
                        tabTuileMovable.add(this.taquin.getBoard()[ligne][colonne].tuileMovable(this.taquin));
                    }
                }
            }


            TuileMovable tuileMovable = null;
            boolean stop = false;
            int id = 0;

            while (!(stop == true)){
                System.out.println("Les tuiles déplaçables disponibles sont : " + tabTuileMovable);

                boolean idValidate = false;

                // Partie choix de l'id
                while (!(idValidate == true)){
                    try{
                        // L'utilisateur va rentrer un nombre entre 1 et la taille du taquin - 1
                        System.out.println("Veuillez choisir une id  1.." + (this.tailleTaquin-1) + " :");
                        Scanner scanID = new Scanner(System.in);
                        id = scanID.nextInt();
                        System.out.println("");

                        // On dispose de x tuiles, si id est compris entre 1 et la taille du taquin -1 alors on passe à la suite sinon on recommance
                        if (id > 0 && id < this.tailleTaquin){
                            idValidate = true;
                        }
                        else{
                            idValidate = false;
                        }
                    }

                    catch(Exception InputMismatchException){
                        System.out.println("La saisie est incorrecte !!!");
                    }
                }

                // Partie choix d'id
                // ici on cherche la tuile movable ayant comme identifiant l'id
                for (int i = 0; i < tabTuileMovable.size(); i++){
                    if (tabTuileMovable.get(i).getTuile().getIdentifiant() == id){
                        tuileMovable = tabTuileMovable.get(i);
                        stop = true;
                        break;
                    }
                }
            }
    
            // On déplace la tuile en fonction de l'action
            if (tuileMovable.getNumeroAction() == 1){
                tuileMovable.getTuile().haut(this.taquin);
            }
            else if (tuileMovable.getNumeroAction() == 2){
                tuileMovable.getTuile().bas(this.taquin);
            }
            else if (tuileMovable.getNumeroAction() == 3){
                tuileMovable.getTuile().gauche(this.taquin);
            }
            else{
                tuileMovable.getTuile().droite(this.taquin);
            }
            
            this.taquin.etatSuivant();
            
        }
    }

}