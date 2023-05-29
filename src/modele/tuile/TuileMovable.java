package tuile;

public class TuileMovable{
    private Tuile tuile;
    private int numeroAction;

    /**
        * Le constructeur permet d'instancier une TuileMovable
        * @param tuile correspond à un objet Tuile
        * @param numeroAction correspond à un numéro de déplacement entre 1 et 4 compris (haut, bas, gauche, droite)
    */
    public TuileMovable(Tuile tuile, int numeroAction){
        this.tuile = tuile;
        this.numeroAction = numeroAction;
    }

    /** 
        * Cet accesseur permet d'obtenir la tuile
        * @return la tuile
    */
    public Tuile getTuile(){
        return this.tuile;
    }

    /** 
        * Cet accesseur permet d'obtenir le numéro d'action
        * @return le numéro d'action
    */
    public int getNumeroAction(){
        return this.numeroAction;
    }

    @Override
    public String toString(){
        if (this.numeroAction == 1){
            return "( " + this.tuile + " HAUT )" ;
        }
        if (this.numeroAction == 2){
            return "( " + this.tuile + " BAS )" ;
        }
        if (this.numeroAction == 3){
            return "( " + this.tuile + " GAUCHE )" ;
        }
        if (this.numeroAction == 4){
            return "( " + this.tuile + " DROITE )" ;
        }
        return "";
    }

}