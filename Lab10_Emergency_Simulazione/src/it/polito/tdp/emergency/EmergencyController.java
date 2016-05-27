package it.polito.tdp.emergency;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.emergency.model.Modello;
import it.polito.tdp.emergency.model.Simula;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EmergencyController {
	
	Modello model = new Modello();
	

    public void setModel(Modello model) {
		this.model = model;
	}

  

        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML // fx:id="txtOre"
        private TextField txtOre; // Value injected by FXMLLoader

        @FXML // fx:id="txtDottore"
        private TextField txtDottore; // Value injected by FXMLLoader

        @FXML // fx:id="txtResult"
        private TextArea txtResult; // Value injected by FXMLLoader

        @FXML // fx:id="btnSimula"
        private Button btnSimula; // Value injected by FXMLLoader

        @FXML // fx:id="btnInserisci"
        private Button btnInserisci; // Value injected by FXMLLoader

    @FXML
    void doSimula(ActionEvent event) {
    	boolean controllo = model.controlloInputNome(this.txtDottore.getText());
    	if(!controllo){this.txtResult.appendText("Caratteri nomi non corretti o manca almeno un dottore"+"\n");return;}
    	boolean controllo1 = model.controlloInputOre(this.txtOre.getText());
    	if(!controllo1){this.txtResult.appendText("Le ore devono essere numeri interi"+"\n");return;}
    	int ore = Integer.parseInt(this.txtOre.getText());
    	boolean presenzaDottore=model.controlloPresenzaDottore();
    	if(!presenzaDottore){this.txtResult.appendText("deve essere inserito almeno 1 dottore"+"\n");return;}
    	model.creaTabellaPazienti(ore);
    	Simula s = new Simula();
    	String result = s.simula();
    	this.txtResult.appendText(result);

    }

    @FXML
    void doInserisci(ActionEvent event) {
    	this.txtResult.clear();
    	boolean controllo = model.controlloInputNome(this.txtDottore.getText());
    	if(!controllo){this.txtResult.appendText("Caratteri nomi non corretti"+"\n");return;}
    	boolean controllo1 = model.controlloInputOre(this.txtOre.getText());
    	if(!controllo1){this.txtResult.appendText("Le ore devono essere numeri interi"+"\n");return;}
    	String result = model.inserisciDottore(this.txtDottore.getText(), this.txtOre.getText());
    	this.txtResult.appendText(result);
       }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtOre != null : "fx:id=\"txtOre\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtDottore != null : "fx:id=\"txtDottore\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnInserisci != null : "fx:id=\"btnInserisci\" was not injected: check your FXML file 'Emergency.fxml'.";

    }
}

