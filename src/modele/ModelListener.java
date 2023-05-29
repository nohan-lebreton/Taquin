package modele;

public interface ModelListener{
    /** 
        * Cette méthode doit être redéfini et permet de mettre à jour le model
        * @param source correspond à un objet source
    */
    public void modelUpdated(Object source);
}