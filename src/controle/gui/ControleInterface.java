package gui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import taquin.Taquin;
import tuile.Tuile;
import tuile.TuileMovable;

import gui.DisplayInterface;
import terminal.Display;

import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;


public class ControleInterface extends JFrame implements KeyListener{
    private Taquin taquin;
    private int window_width = 400;
    private int window_height = 600;
    private int nbLignes;
    private int nbColonnes;
    private JPanel jpanel;
    private String cheminImage = "./dist/assets/images/img_03.jpg";

    // Variables pour accèder aux sous-panels
    private JPanel accederPanelGridTaquin ;
    private JPanel accederPanelBoutons;
    private JPanel accederPanelInfo;

    /**
        * Le constructeur permet d'instancier les contrôles d'interface du jeu
        * @param newTaquin correspond au taquin courant
    */
    public ControleInterface(Taquin newTaquin){
        super("Taquin");
        this.taquin = newTaquin;
        this.nbLignes = this.taquin.getLignes();
        this.nbColonnes = this.taquin.getColonnes();

        this.setSize(this.window_width,this.window_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.addKeyListener(this);
        this.setFocusable(true);

        this.init();
    }

    /**
        * Cet accesseur permet de récupérer la fenêtre
        * @return une JFrame
    */
    public JFrame getJFrame(){
        return this;
    }

    /**
        * Cett méthode permet d'initialiser les composants de la fenêtre
    */
    private void init(){
        this.creationPanelJeu();
    }

    // --------------------------------------------------------------------------------------

    /**
        * Cette méthode permet d'initialiser les panels, en les lient dans un seul panel lié à cette fenêtre
    */
    private void creationPanelJeu(){
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        this.accederPanelGridTaquin = this.panelGridTaquin();
        contentPane.add(this.accederPanelGridTaquin, BorderLayout.CENTER);

        this.accederPanelBoutons = this.panelBoutons();
        contentPane.add(this.accederPanelBoutons, BorderLayout.SOUTH);

        this.accederPanelInfo = this.panelInfo();
        contentPane.add(this.accederPanelInfo, BorderLayout.NORTH);
        
        // On recupère le contentPane pour l'utiliser plus tard
        this.jpanel = contentPane;
    }

    /**
        * Cette méthode permet de bloquer les composants d'un panel
        * @param panel correpond à un panel
        * @return un JPanel où les composants sont désactivés
    */
    private JPanel bloquerComposants(JPanel panel){
        // On parcourt tous les composants qui ce trouve dans le panel
        for(Component c : panel.getComponents()){
            // Permet de récupérer bloquer chaque composant
            c.setEnabled(false);
        }
        return panel;
    }

    // --------------------------------------------------------------------------------------

    /**
        * Cette méthode permet de découper une image en plusieurs parties
        * Récupéré sur https://kalanir.blogspot.com/2010/02/how-to-split-image-into-chunks-java.html?fbclid=IwAR1o6QO9EVY8hs2tFPoZmkUYlI28GLdt82YaJqYs1HQCNGgxlgU8bnsGROw
        * @param image correspond à l'image chargé 
        * @param nbRow correspond au nombre de découpe qu'on souhauite effectuer en hauteur
        * @param nbCol correspond au nombre de découpe qu'on souhauite effectuer en largeur
        * @return un tableau de BufferedImage contenant chaque partie de l'image
    */
    private BufferedImage[] splitImgToList(BufferedImage image, int nbRow, int nbCol){
        int dimension = nbRow  * nbCol;
        int imageWidth = image.getWidth() / nbCol;
        int imageHeight = image.getHeight() / nbRow;
        int tmp = 0;
        BufferedImage imgsPart[] = new BufferedImage[dimension ];

        for(int i = 0; i < nbRow; i++){
            for(int j = 0;j < nbCol; j++){
                imgsPart[tmp] = new BufferedImage(imageWidth,imageHeight,image.getType());
                Graphics2D graph = imgsPart[tmp++].createGraphics();
                graph.drawImage(image, 0, 0, imageWidth , imageHeight , imageWidth  * j, imageHeight  * i, imageWidth  * j + imageWidth ,imageHeight  * i + imageHeight , null);
                graph.dispose();
            }
        }
        return imgsPart;
    }

    // --------------------------------------------------------------------------------------


    /**
        * Cette méthode permet d'instancier le panel représentant le taquin.
        * @return un JPanel
    */
    private JPanel panelGridTaquin (){

        File file = new File(this.cheminImage);

        try {
            // On charge l'image et la découpe en plusieurs morceaux en fonction de la taille du taquin, puis on les mets dans une liste.
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);
            BufferedImage[] tabImage = this.splitImgToList(image, this.taquin.getLignes(), this.taquin.getColonnes());

            // On affecte une image à une tuile dans l'ordre du taquin initial
            int cpt = 0;
            for (int i = 0; i < this.taquin.getBoardInit().length; i++){
                for (int j = 0; j < this.taquin.getBoardInit()[0].length; j++){
                    this.taquin.getBoardInit()[i][j].setImage(tabImage[cpt]);
                    cpt++;
                }
            }

            JPanel contentPaneGrid = new JPanel(new GridLayout(this.nbLignes, this.nbColonnes));

            // On represente le taquin en temps réel, on parcours le taquin pour donner aux boutons le bon identifiant et la bonne image
            Tuile[][] plateauDerange = this.taquin.getBoard();
            for (int ligne = 0; ligne < this.taquin.getLignes(); ligne ++){
                for (int colonne = 0; colonne < this.taquin.getColonnes(); colonne ++){
                    JButton btn = new JButton("" + plateauDerange[ligne][colonne], new ImageIcon(plateauDerange[ligne][colonne].getImage()));           
                    btn.addActionListener(this::btnTuilesListener);

                    // Si c'est la case vide on la désactive
                    if (plateauDerange[ligne][colonne].getIdentifiant() == 0){
                        btn.setEnabled(false);
                    }
                    // Sinon on l'active
                    else{
                        btn.setEnabled(true);
                    }
                    contentPaneGrid.add(btn);
                }
            }
            return contentPaneGrid;


        } catch (IOException e) {
            return null;
        }    
    }

    /**
        * Cette méthode écoute et lance une action de déplacement lorsqu'on clique sur un bouton si c'est possible.
        * @param event correspond au bouton où on clique
    */
    private void btnTuilesListener(ActionEvent event){

        int tuileId = Integer.MIN_VALUE;

        // On parcourt tous les composants qui ce trouve dans le panel
        for(Component btn : this.panelGridTaquin().getComponents()){
            // Permet de récupérer le bouton que l'utilisateur souhaite quand il clique dessus
            if(event.getActionCommand().equals(btn.getAccessibleContext().getAccessibleName())){
                tuileId = Integer.parseInt(event.getActionCommand());
                break;
            }
        }

        // On crée une TuileMovable pour avoir à la fois la tuile et la direction qui marche
        Tuile[][] plateauCourant = this.taquin.getBoard();
        TuileMovable tuileSelectionnee = null;
        for(int ligne = 0; ligne < this.taquin.getLignes(); ligne++){
            for(int colonne = 0; colonne < this.taquin.getColonnes(); colonne++){
                if (plateauCourant[ligne][colonne].getIdentifiant() == tuileId){
                    tuileSelectionnee = plateauCourant[ligne][colonne].tuileMovable(this.taquin);
                    break;
                }
            }
        }
        
        if (!(tuileSelectionnee == null)){
            if (tuileSelectionnee.getNumeroAction() == 1){
                tuileSelectionnee.getTuile().haut(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 2){
                tuileSelectionnee.getTuile().bas(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 3){
                tuileSelectionnee.getTuile().gauche(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 4){
                tuileSelectionnee.getTuile().droite(this.taquin);
            }
            this.refresh();           
        }
    }

     /**
        * Cette méthode permet de mettre à jour l'affichage
    */
    private void refresh(){
        // Indique aux écouteurs que l'état à changé
        this.taquin.etatSuivant();
        // on supprime les panels de la fenetre 
        this.jpanel.removeAll();
        // on les remets mais c les versions modifiées
        this.init();
        // On refresh la frame
        this.repaint();
        this.setVisible(true);
    }
    

    // --------------------------------------------------------------------------------------


    /**
        * Cette méthode permet d'instancier le panel représentant les boutons d'actions
        * @return un JPanel
    */
    private JPanel panelBoutons(){
        JPanel contentPaneFlow = new JPanel(new FlowLayout());

        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(this::btnRestartListener);
        contentPaneFlow.add(restartButton);

        JButton finishButton = new JButton("FINISH");
        finishButton.addActionListener(this::btnFinishListener);
        contentPaneFlow.add(finishButton);

        JButton trickButton = new JButton("TRICK");
        trickButton.addActionListener(this::btnTrickListener);
        contentPaneFlow.add(trickButton);

        return contentPaneFlow;
    }

    /**
        * Cette méthode écoute et lance une action de fin lorsqu'on clique sur le bouton, affiche un popup et bloque les boutons tuiles ainsi que le bouton finish.
        * @param event correspond au bouton où on clique
    */
    private void btnFinishListener(ActionEvent event){
        // On récupère les plateaux
        Tuile[][] tabInit = this.taquin.getBoardInit();
        Tuile[][] tabFinal = this.taquin.getBoard();

        JFrame popUp = new JFrame();
        // On demande à l'utilisateur si il est sur de son choix
        int result = JOptionPane.showConfirmDialog(popUp, "Vous en êtes sûr ?.");

        if (result == 0){
            // Permet de bloquer les cases
            this.accederPanelGridTaquin = this.bloquerComposants(this.accederPanelGridTaquin);
            // Permet de bloquer les touches du clavier
            this.removeKeyListener(this);

            // Permet de désactiver le bouton finish
            for (Component c : this.accederPanelBoutons.getComponents()){
                if (c.equals(event.getSource())){
                    c.setEnabled(false);
                    break;
                }
            }

            // On affiche un pop up si l'utilisation à gagné ou pas
            if(this.taquin.isOver(tabInit,tabFinal) == true){
                JOptionPane.showMessageDialog(popUp,"Vous avez gagné !!!");
            }
            else{
                JOptionPane.showMessageDialog(popUp,"Vous avez perdu !!!");
            }

            
        }
        
    }
    
    /**
        * Cette méthode écoute et lance une action de remise à zéro lorsqu'on clique sur le bouton, affiche un popup et remet à zéro le taquin et le mélange.
        * @param event correspond au bouton où on clique
    */
    private void btnRestartListener(ActionEvent event){
        JFrame popUp = new JFrame();
        // On demande à l'utilisateur si il est sur de son choix
        int result = JOptionPane.showConfirmDialog(popUp, "Vous en êtes sûr ?.");

        if (result == 0){
            // On créé un nouveau taquin
            Taquin newTaquin = new Taquin(this.taquin.getLignes(), this.taquin.getColonnes());
            
            // On remplace le taquin existant par le nouveau
            this.taquin = newTaquin;
            System.out.println("\u001B[41m" + "!!!!!!!!!!!!!! RESTART !!!!!!!!!!!!!!!" +"\u001B[0m" + "\n");

            this.taquin.deranger();

            // On réactive les touches du clavier
            this.addKeyListener(this);

            // On créé un nouveau display pour l'affichage console
            Display affichageTaquin = new Display(this.taquin);

            this.refresh();
        }
    }


    /**
        * Cette méthode écoute et lance une action d'affichage lorsqu'on clique sur le bouton, affiche l'image du taquin à l'état initial dans un popup.
        * @param event correspond au bouton où on clique
    */
    private void btnTrickListener(ActionEvent event){
        
        // PopUp contenant le but de l'utilisateur
        JFrame popUp = new JFrame();
        JDialog panel = new JDialog(popUp);

        panel.setLayout(new BorderLayout());
        panel.setSize(400,400);
        panel.setLocationRelativeTo(null);

        JLabel labelText = new JLabel("Taquin état initial :");
        labelText.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelText,BorderLayout.NORTH);

        JLabel labelImage = new JLabel();
        labelImage.setIcon(new ImageIcon(this.cheminImage));
        labelImage.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(labelImage, BorderLayout.CENTER);

        panel.pack();
        panel.setResizable(false);

        panel.setVisible(true);  
    }



    // --------------------------------------------------------------------------------------


    /**
        * Cette méthode permet d'instancier le panel représentant les informations
        * @return un JPanel
    */
    private JPanel panelInfo(){
        JPanel contentPaneFlow = new JPanel(new FlowLayout());

        JLabel labelRule = new JLabel("Le taquin est mélangé, veuillez le remettre en ordre.");

        contentPaneFlow.add(labelRule);

        return contentPaneFlow;
    }


    // --------------------------------------------------------------------------------------

    // On active l'évenement de focus sur la fenêtre,  ce qui permet à la fenêtre d'obtenir ici l'évenement clavier
    @Override
    public void setFocusable(boolean b) {
        super.setFocusable(b);
    }

    // --------------------------------------------------------------------------------------

    @Override
	public void keyPressed(KeyEvent e){

        int numeroAction = 0;
        if(e.isActionKey()){
            switch(e.getKeyCode()){
                // HAUT
                case KeyEvent.VK_UP :
                    numeroAction = 1;
                    System.out.println(numeroAction);
                    break;
                
                // BAS
                case KeyEvent.VK_DOWN :
                    numeroAction = 2;
                    System.out.println(numeroAction);
                    break;

                // GAUCHE
                case KeyEvent.VK_LEFT :
                    numeroAction = 3;
                    System.out.println(numeroAction);
                    break;

                // DROITE
                case KeyEvent.VK_RIGHT :
                    numeroAction = 4;
                    System.out.println(numeroAction);
                    break;
            }
        }

        // On crée une TuileMovable pour avoir à la fois la tuile et la direction qui marche
        Tuile[][] plateauCourant = this.taquin.getBoard();
        TuileMovable tuileSelectionnee = null;
        for(int ligne = 0; ligne < this.taquin.getLignes(); ligne++){
            for(int colonne = 0; colonne < this.taquin.getColonnes(); colonne++){
                // On vérifie si la tuileMovable n'est pas null, et que son numéroAction est égale au numéro retourné par la touche.
                if (!(plateauCourant[ligne][colonne].tuileMovable(this.taquin) == null) && plateauCourant[ligne][colonne].tuileMovable(this.taquin).getNumeroAction() == numeroAction){
                    tuileSelectionnee = plateauCourant[ligne][colonne].tuileMovable(this.taquin);
                    break;
                }
            }
        }

        if (!(tuileSelectionnee == null)){
            if (tuileSelectionnee.getNumeroAction() == 1){
                tuileSelectionnee.getTuile().haut(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 2){
                tuileSelectionnee.getTuile().bas(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 3){
                tuileSelectionnee.getTuile().gauche(this.taquin);
            }
            else if (tuileSelectionnee.getNumeroAction() == 4){
                tuileSelectionnee.getTuile().droite(this.taquin);
            }
            this.refresh();           
        }
    
	}

    // Pas d'utilité pour l'instant
	@Override
	public void keyTyped(KeyEvent e){
        // Vide
	}

    // Pas d'utilité pour l'instant
	@Override
	public void keyReleased(KeyEvent e){
        // Vide
	}
}
