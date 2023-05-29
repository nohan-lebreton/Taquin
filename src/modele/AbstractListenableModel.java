package modele;

import java.util.ArrayList;

public abstract class AbstractListenableModel{
    protected ArrayList<ModelListener> listeners;

    /** 
        * Le constructeur permet d'instancier une classe abstraite qui permet d'instancier des écouteurs qui seront assigné au model 
    */
    public AbstractListenableModel(){
        this.listeners = new ArrayList<ModelListener>();
    }

    /** 
        * Cette méthode permet d'ajouter un écouteur au composant
        * @param ecouteur correspond à un écouteur assigné au model
    */
    public void addListener(ModelListener ecouteur){
        this.listeners.add(ecouteur);
    }

    /** 
        * Cette méthode permet de supprimer un écouteur du composant
        * @param ecouteur correspond à un écouteur assigné au model 
    */
    public void removeListener(ModelListener ecouteur){
        this.listeners.remove(ecouteur);
    }

    /** 
        * Cette méthode permet de mettre à jour tous les écouteurs
    */
    protected void deplacementTuile(){
        for (ModelListener ecouteur : this.listeners){
            ecouteur.modelUpdated(this);
        }
    }

}