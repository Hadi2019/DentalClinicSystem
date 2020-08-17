package application;

/*import javafx.collections.FXCollections;
import javafx.collections.ObservableList;*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class DentalPaymentSystemController {
	
	@FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldAddress;
	
    @FXML
    private RadioButton radioButtonSenior;

    @FXML
    private ToggleGroup ageStage;

    @FXML
    private RadioButton radioButtonKid;

    @FXML
    private RadioButton radioButtonAdult;


    @FXML
    private ComboBox<String> comboBoxProvince;
    
    @FXML
    private Label labelTotalFees;
    
    @FXML
    private CheckBox checkBoxFlossing;

    @FXML
    private CheckBox checkBoxFilling;

    @FXML
    private CheckBox checkBoxRootCanal;
   
    @FXML
    private TextArea textAreaError;


    //ObservableList<String> provinces = FXCollections.observableArrayList("Alberta", "Ontario", "Quebec", "Other");
    
  //put this line inside initialize(); : comboBoxProvince.setItems(provinces);
	
	public void initialize() {
		//configuring the combobox 	
		comboBoxProvince.getItems().addAll("Choose Province", "Alberta %7", "Ontario %13", "Quebec %6", "Other");
		comboBoxProvince.getSelectionModel().select(0);
		
	}
	
	// Calculate Button
	@FXML
    void button_CalculateClicked(ActionEvent event) {
		//clearTextArea();
		textAreaError.setText("");

		try
		{
			if (validate()) {
				/*labelErrorMessage.setText("");
				labelErrorMessage2.setText("");
				labelErrorMessage3.setText("");*/
				double subtotal =0, discount, tax, beforeTax, total;
				discount = discount(calculateSubtotal());
				beforeTax = calculateSubtotal() - discount;
				tax = taxProvince(beforeTax);
				total = beforeTax + tax;
				labelTotalFees.setText("$" + Double.toString(total));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	// Reset Button
	@FXML
	public void reset_ButtonClicked() {
		/*labelErrorMessage.setText("");
		labelErrorMessage3.setText("");
		labelErrorMessage2.setText("");*/
		textAreaError.setText("");
		labelTotalFees.setText("");
		radioButtonKid.setSelected(false);
		radioButtonAdult.setSelected(true);
		radioButtonSenior.setSelected(false);
		checkBoxFlossing.setSelected(false);
		checkBoxFilling.setSelected(false);
		checkBoxRootCanal.setSelected(false);
		//comboBoxProvince.getValue().indexOf(0);
		textFieldName.setText("");
		textFieldAddress.setText("");		
		comboBoxProvince.setValue("Choose Province");
	}
	
	// a method to get the subtotal (before discount and tax)
	public double calculateSubtotal() {
			double subtotal =0;
			if (checkBoxFlossing.isSelected())
				subtotal += 20;
			if (checkBoxFilling.isSelected())
				subtotal += 75;
			if (checkBoxRootCanal.isSelected())
				subtotal += 150;
				
			return subtotal;
		}
	
	// a method to get the discount on the subtotal
	public double discount(double subtotal) {
		double discount=0;
			if (ageStage.getSelectedToggle().equals(radioButtonSenior))
				discount = subtotal * 0.10;
			else if (ageStage.getSelectedToggle().equals(radioButtonKid))
				discount = subtotal * 0.15;
		return discount;
	}
	
	// a method to get the tax amount
	// a method to get the tax amount
	public double taxProvince(double subtotal) {
		double taxAmount=0;
		if (this.comboBoxProvince.getValue().equals("Ontario %13"))
			taxAmount = subtotal * 0.13;
		if (this.comboBoxProvince.getValue().equals("Quebec %6"))
			taxAmount = subtotal * 0.06;
		if (this.comboBoxProvince.getValue().equals("Alberta %7"))
			taxAmount = subtotal * 0.07;
		
		return taxAmount;
	}


	public boolean validate ( ) {
		boolean valid = true;
		if (!checkBoxFlossing.isSelected() && !checkBoxFilling.isSelected() && !checkBoxRootCanal.isSelected()) {
			//labelErrorMessage.setText("Error: You have to choose at least one service !");
			textAreaError.appendText("You have to choose at least one service\n");
			valid = false;
		}
		if (textFieldName.getText().equals(""))
		{
			textAreaError.appendText("Error: You have to enter a name\n");
			valid = false;
		}
		if (textFieldAddress.getText().equals(""))
		{
			textAreaError.appendText("Error: you have to enter an address\n");
			valid = false;
		}
		if (this.comboBoxProvince.getValue().equals("Choose Province")) {
			textAreaError.appendText("Error: you have to choose a province");
			labelTotalFees.setText("");
			valid = false;
		}
		return valid;
	}
	
	public void clearTextArea() {
		textAreaError.clear();
	}
}
