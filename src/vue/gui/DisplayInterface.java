package gui;
import javax.swing.*;
import java.awt.*;
import modele.ModelListener;
import taquin.Taquin;
import gui.ControleInterface;

public class DisplayInterface extends JPanel implements ModelListener{
    private Taquin taquin;
    private JFrame frame;
    private ControleInterface CI;

    /**
        * Le constructeur permet d'instancier un objet DisplayInterface
        * @param newTaquin correspond à un objet Taquin
    */
    public DisplayInterface(Taquin newTaquin){
        super();
        this.taquin = newTaquin;
        this.taquin.addListener(this);

        this.CI = new ControleInterface(newTaquin);
        this.frame = this.CI.getJFrame();
    }

    /** 
        * Cette méthode permet de mettre à jour le model
        * @param source correspond à un objet source
    */
    @Override
    public void modelUpdated(Object source){
        this.frame.setVisible(true);    
    }

}
