package main;

import  taquin.Taquin;
import  tuile.Tuile;
import  terminal.Display;
import  gui.DisplayInterface;
import  java.util.Scanner;
import  terminal.ControleTerminal;
import  gui.ControleInterface;


public class Main{
    public static void main(String[] args){

        /*
            Objectif :
                -> pouvoir jouer au clavier
                -> générer javadoc
                -> Rapport
                -> faire archive jar
        */

        
        // Initialisation du jeu
        Taquin taquin = new Taquin(3,3);
        taquin.deranger();

        // Choix de l'affichage
        int choix = 0;
        boolean valide = false;

        while(!(valide == true)){
            try{
                System.out.println("Choisissez une option : \nInterface graphique (1) \nTerminal (2)");
                Scanner scanChoix = new Scanner(System.in);
                choix = scanChoix.nextInt();
                System.out.println("");
                if (choix >= 1 && choix <= 2){
                    valide = true;
                }
            }

            catch(Exception InputMismatchException){
                System.out.println("La saisie est incorrecte !!!");
            }
        }

        // Si on choisi l'un ou l'autre on affiche dans la console l'évolution du taquin
        Display affichageTaquin = new Display(taquin);
        affichageTaquin.modelUpdated(taquin);
        
        // Choix de l'interface graphique
        if (choix == 1){
            DisplayInterface affichageTaquinInterface = new DisplayInterface(taquin);
            affichageTaquinInterface.modelUpdated(taquin);
            ControleInterface controlI = new ControleInterface(taquin);
        }

        // Choix du terminal
        else{
            ControleTerminal control = new ControleTerminal(taquin);
        }

    }
}

    
