package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FirstSceneController implements Initializable {

	@FXML
	AnchorPane anchor = new AnchorPane();
	@FXML
	Button chooseFileButton = new Button();
	@FXML
	TextField tf = new TextField();
	@FXML
	Button enterTextButton = new Button();
	@FXML
	TextArea ta = new TextArea();

	ArrayList<String> codes = new ArrayList<String>() {
		private static final long serialVersionUID = -2337034290157561970L;
		{
			add("TI");
			add("PY");
			add("SC");
			add("JI");
			add("SN");
			add("TC");
			add("DI");
			add("OA");
			add("ID");
		}
	};

	Analyzer analyzer = new Analyzer(codes);
	HashSet<String> set = new HashSet<String>();
	ArrayList<String> enteredCodes = new ArrayList<String>();

	String fileName = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		anchor.setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode() == KeyCode.ENTER) {
				textButtonPressed(new ActionEvent());
			}
		});
	}

	public void chooseFileButtonPressed(ActionEvent ev) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select the text file");
		try {
			List<File> files = fc.showOpenMultipleDialog((Stage) chooseFileButton.getScene().getWindow());
			if (files.size() != 0) {
				for (File f : files) {
					analyzer = new Analyzer(codes);
					fileName = f.getAbsolutePath();
					if (enteredCodes.size() == 0) {
						analyzer.processFile(fileName);
					} else {
						analyzer.processFile(fileName, enteredCodes);
					}
				}
			}
		} catch (NullPointerException n) {
			ta.setText(" No files selected");
		}
	}

	public void textButtonPressed(ActionEvent ev) {
		set.add(tf.getText().toUpperCase());
		enteredCodes.clear();
		enteredCodes.addAll(set);
		tf.clear();
		ta.setText(printCodes());
	}

	private String printCodes() {
		String s = "";
		for (String st : enteredCodes) {
			s += " " + st;
		}
		return s;
	}

}
