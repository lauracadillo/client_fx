package com.example.client_fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.fxml.FXML;


import java.io.*;

/**
 * La classe ClientFX fournit une interface utilisateur pour afficher et inscrire les étudiants aux cours.
 */

public class ClientFX extends Application {


    /**
     * Méthode pour démarrer l'application JavaFX. Elle initialise l'interface utilisateur avec la liste des cours et un formulaire d'inscription.
     * @param stage  la fenêtre de l'application
     * @throws Exception si une erreur se produit
     */
    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: beige;");

        /**
         * LISTE DES COURS
         */

        VBox listeCours = new VBox();
        listeCours.setPrefWidth(500);

        Text cours = new Text("Liste des cours");
        cours.setTextAlignment(TextAlignment.CENTER);
        cours.setFont(Font.font("sans serif", 20));

        TableView<ModeleCourse> TableauCourses = new TableView<ModeleCourse>();
        TableColumn<ModeleCourse, String> coursCode = new TableColumn<>("Code");
        coursCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        coursCode.setPrefWidth(150);

        TableColumn<ModeleCourse, String> coursName = new TableColumn<>("Name");
        coursName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coursName.setPrefWidth(250);
        TableauCourses.getColumns().addAll(coursCode, coursName);

        listeCours.setPadding(new Insets(0,50,0,50));

        HBox options = new HBox();
        ChoiceBox selectSession = new ChoiceBox<>();
        selectSession.getItems().add("Automne");
        selectSession.getItems().add("Hiver");
        selectSession.getItems().add("Ete");

        TableView.TableViewSelectionModel selectionModel = TableauCourses.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList selectedItems = selectionModel.getSelectedItems();

        Button charger = new Button("charger");
        /**
         * Réagit au clic de l'utilisateur sur le bouton "charger".
         * Récupère la session sélectionnée par l'utilisateur et remplit le tableau des cours avec les données de la session choisie.
         * @param actionEvent un événement de clic sur le bouton "charger"
         * */

        charger.setOnAction(actionEvent -> {

            String sessionChoisie = (String) selectSession.getValue();
            Controler.fillTable(sessionChoisie).clear();
            TableauCourses.setItems(Controler.fillTable(sessionChoisie));

        });

        options.getChildren().addAll(selectSession, charger);
        options.setAlignment(Pos.CENTER);
        options.setSpacing(100);
        options.setPadding(new Insets(20));

        listeCours.getChildren().addAll(cours, TableauCourses,  options);
        listeCours.setAlignment(Pos.CENTER);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setStyle("-fx-border-width: 2px;");

        /**
         * INSCRIPTION AUX COURS
         */

        VBox inscription = new VBox();
        inscription.setPrefWidth(500);

        Text formTitle = new Text("Formulaire d'inscription");
        formTitle.setFont(Font.font("sans serif", 20));

        Text prenom = new Text("Prénom");
        TextField prenomField = new TextField();
        Text nom = new Text("Nom");
        TextField nomField = new TextField();
        Text email = new Text("Email");
        TextField emailField = new TextField();
        Text matricule = new Text("Matricule");
        TextField matriculeField = new TextField();

        GridPane gridFormulaire = new GridPane();
        gridFormulaire.setHgap(10);
        gridFormulaire.setVgap(10);

        gridFormulaire.add(nom, 0, 0);
        gridFormulaire.add(nomField, 3, 0);
        gridFormulaire.add(prenom, 0, 1);
        gridFormulaire.add(prenomField, 3, 1);
        gridFormulaire.add(email, 0, 2);
        gridFormulaire.add(emailField, 3, 2);
        gridFormulaire.add(matricule, 0, 3);
        gridFormulaire.add(matriculeField, 3, 3);

        VBox donnees = new VBox();
        VBox fields = new VBox();
        HBox formulaire = new HBox();

        formulaire.getChildren().add(gridFormulaire);
        formulaire.setAlignment(Pos.CENTER);
        formulaire.setPadding(new Insets(20));


        formulaire.getChildren().addAll(donnees, fields);
        Button envoyer = new Button("envoyer");
        envoyer.setPrefWidth(100);
        /**
         * Réagit au clic de l'utilisateur sur le bouton "envoyer".
         * Récupère les informations entrées par l'utilisateur et les valide.
         * Si les données sont valides, inscrit l'utilisateur au cours sélectionné et enregistre les données dans un fichier.
         * Si les données ne sont pas valides, affiche un message d'erreur.
         * @param actionEvent un événement de clic sur le bouton "envoyer"
         */
        envoyer.setOnAction(actionEvent -> {
            String nomInscription = nomField.getText();
            String prenomInscription = prenomField.getText();
            String emailInscription = emailField.getText();
            String matriculeInscription = matriculeField.getText();
            String incorrect = "";
            if (!emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" )){
                incorrect += "Le champ email est invalide \n";
            }
            if (!matriculeField.getText().matches("^[0-9]{8}$")){
                incorrect += "Le champ matricule est invalide \n ";
            }
            if(selectedItems.isEmpty()){
                incorrect += "Vous devez selectioner un cours! \n";
            }
            if(!incorrect.isEmpty()){
                Alert incorrectDonnes = new Alert(Alert.AlertType.ERROR);
                incorrectDonnes.setContentText("Le formulaire est invalide");
                incorrectDonnes.setContentText(incorrect);
                incorrectDonnes.showAndWait();
            }
            else {
                String coursSelectionne = selectedItems.toString();
                String donneesInscription = " " + matriculeInscription + " " + nomInscription + " " + prenomInscription +" "+ emailInscription;
                try {
                    Alert FinInscription = new Alert(Alert.AlertType.CONFIRMATION);
                    FinInscription.setContentText("Felicitations! "+ nomInscription +" " + prenomInscription +" est inscrit(e) avec succes pour le cours!" + coursSelectionne);
                    FinInscription.showAndWait();

                    FileWriter writer = new FileWriter("src/main/java/com/example/client_fx/inscription.txt", true);

                    writer.write(coursSelectionne);
                    writer.write(donneesInscription);
                    writer.write("\n");
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        inscription.getChildren().addAll(formTitle, formulaire, envoyer);

        inscription.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(listeCours, separator, inscription);

        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Inscription UdeM");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
