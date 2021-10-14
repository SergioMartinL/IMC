package dad.IMC;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private TextField pesoText;
	private TextField alturaText;
	private Label imcText;
	private Label pesoLabel;
	private Label kg;
	private Label alturaLabel;
	private Label cm;
	private Label imcLabel;
	private Label estadoLabel;
	
	private DoubleProperty pesoNum=new SimpleDoubleProperty();
	private DoubleProperty alturaNum=new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		pesoText=new TextField("");
		alturaText=new TextField("");
		imcText=new Label();
		pesoLabel=new Label("Peso: ");
		kg=new Label(" kg");
		alturaLabel=new Label("Altura: ");
		cm=new Label(" cm");
		imcLabel=new Label("IMC: ");
		estadoLabel=new Label();
		
		HBox peso=new HBox(5,pesoLabel,pesoText,kg);
		HBox altura=new HBox(5,alturaLabel,alturaText,cm);
		HBox imc=new HBox(5,imcLabel,imcText);
		
		VBox root=new VBox(5,peso,altura,imc,estadoLabel);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);
		
		Scene scene=new Scene(root,320,200);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Bindings.bindBidirectional(pesoText.textProperty(), pesoNum, new NumberStringConverter());
		Bindings.bindBidirectional(alturaText.textProperty(), alturaNum, new NumberStringConverter());
		imcText.textProperty().bind(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).asString());

		StringExpression saludoExpression=
					Bindings
						.concat(
								Bindings
									.when(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).lessThan(18.5))
									.then("Bajo Peso")
									.otherwise(""))
						.concat(
								Bindings
									.when(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).greaterThanOrEqualTo(18.5).and(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).lessThan(25)))
									.then("Normal")
									.otherwise(""))
						.concat(
								Bindings
									.when(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).greaterThanOrEqualTo(25).and(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).lessThan(30)))
									.then("Sobrepeso")
									.otherwise(""))
						.concat(
								Bindings
									.when(pesoNum.divide((alturaNum.divide(100)).multiply(alturaNum.divide(100))).greaterThanOrEqualTo(30))
									.then("Obeso")
									.otherwise(""));
		
		estadoLabel.textProperty().bind(saludoExpression);
	}

	public static void main(String[] args) {
		launch(args);

	}
}
