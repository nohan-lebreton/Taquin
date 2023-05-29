package terminal;
import  taquin.Taquin;
import  tuile.Tuile;
import  modele.ModelListener;
import  modele.AbstractListenableModel;


public class Display implements ModelListener{
    private Taquin taquin;

    /**
        * Le constructeur permet d'instancier un objet Display
        * @param taquin correspond à un objet Taquin
    */
    public Display(Taquin taquin){
        this.taquin = taquin;
        this.taquin.addListener(this);
    }

    /** 
        * Cette méthode permet d'afficher le taquin en console
        * @param taquin correspond au taquin courant
        * @param tabPourLaCopie correspond à un plateau de tuiles
    */
    private void affichageTaquin(Taquin taquin, Tuile[][] tabPourLaCopie){
        Tuile[][] taquinBoardCopy = tabPourLaCopie;
        
        for(int ligne = 0; ligne < taquin.getLignes(); ligne++){
            for(int colonne = 0; colonne < taquin.getColonnes(); colonne++){
                Color color = whichColor(taquinBoardCopy[ligne][colonne]);
                System.out.print( color + " " + taquinBoardCopy[ligne][colonne] + " "  + Color.RESET_COLOR);
            }
            System.out.println();
        }
        System.out.println("");
    }

    /** 
        * Cette méthode permet d'afficher l'état initial du taquin en console
    */
    private void affichageTaquinEtatInit(){
        affichageTaquin(this.taquin, taquin.getBoardInit());
    }

    /** 
        * Cette méthode permet d'afficher l'état suivant du taquin en console
    */
    private void affichageTaquinEtatSuivant(){
        affichageTaquin(this.taquin, taquin.getBoard());
    }
    
    /** 
        * Cette méthode permet de mettre à jour le model
        * @param source correspond à un objet source
    */
    @Override
    public void modelUpdated(Object source){
        System.out.println("\u001B[44m" + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +"\u001B[0m" + "\n");
        System.out.println("Taquin etat initial : ");
        affichageTaquinEtatInit();
        System.out.println("Taquin etat suivant : ");
        affichageTaquinEtatSuivant();
        System.out.println("\u001B[44m" + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +"\u001B[0m" + "\n");

    }

    /** 
        * Cette méthode permet de changer la couleur d'affichage des tuiles en fontion de leur identifiant, tuile identifiant 0 couleur noir, les autres couleur vert.
        * @param tuile correspond à la tuile sur laquelle on souhaite se baser
    */
    private Color whichColor(Tuile tuile){
        switch(tuile.getIdentifiant()){
            case 0 :
                return Color.BLACK_COLOR;

            default :
                return Color.GREEN_COLOR;
        }
    }

}