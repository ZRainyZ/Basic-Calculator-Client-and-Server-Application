package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Controller {

    private String out;

    private Client client;
    private Server server;

    @FXML
    private Button send, clear;

    @FXML
    private ComboBox operation, select1, select2, selectlogic;

    @FXML
    private TextField inputbox, inputbox2, outputbox;

    @FXML
    private Label status, output, input, input2, answer, answer2;

    public void initialize() throws IOException {

        operation.getItems().addAll("Calculator","Logical Operation","Number Base Converter");
        select1.getItems().addAll("Binary","Octal","Decimal","Hexadecimal");
        select2.getItems().addAll("Binary","Octal","Decimal","Hexadecimal");
        selectlogic.getItems().addAll("AND","OR","NAND","NOR","XOR");

        client = new Client();
        server = new Server();
        ServerSocket serverSocket = server.start();
        if(serverSocket != null){
            server.handleRequest(serverSocket);
        }

    }

    @FXML
    private void SeclectOnAction(Event event){

        select1.setOpacity(0);
        select1.setDisable(true);
        select2.setOpacity(0);
        select2.setDisable(true);
        answer.setOpacity(1);
        input2.setOpacity(0);
        answer2.setOpacity(0);
        outputbox.setOpacity(0);
        outputbox.setDisable(true);
        selectlogic.setOpacity(0);
        selectlogic.setDisable(true);

        String selected = operation.getSelectionModel().getSelectedItem().toString();

        if (selected.equals("Calculator")){
            answer.setOpacity(1);
        }
        else if(selected.equals("Logical Operation")){
            answer.setOpacity(0);
            input2.setOpacity(1);
            answer2.setOpacity(1);
            outputbox.setOpacity(1);
            outputbox.setDisable(false);
            selectlogic.setOpacity(1);
            selectlogic.setDisable(false);
        }
        else if (selected.equals("Number Base Converter")){
            select1.setOpacity(1);
            select1.setDisable(false);
            select2.setOpacity(1);
            select2.setDisable(false);
        }
    }

    @FXML
    private void SendOnAction(Event event)throws IOException{

        if (operation.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please select operation", ButtonType.OK);
            alert.show();
            return;
        }

        String selected = operation.getSelectionModel().getSelectedItem().toString();

        if (selected.equals("Calculator")){

            if(inputbox.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter input", ButtonType.OK);
                alert.show();
                return;
            }
            System.out.println(selected);
            out = client.sendInput(selected+","+inputbox.getText());
            inputbox2.setText(out);
        }
        else if(selected.equals("Logical Operation")){

            if(inputbox.getText().equals("") || inputbox2.getText().equals("") || selectlogic.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter input", ButtonType.OK);
                alert.show();
                return;
            }
            out = client.sendInput(selected+","+selectlogic.getSelectionModel().getSelectedItem()+","+inputbox.getText()+","+inputbox2.getText());
            outputbox.setText(out);
        }
        else if(selected.equals("Number Base Converter")) {

            if(inputbox.getText().equals("") || select1.getSelectionModel().getSelectedItem() == null || select2.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter input", ButtonType.OK);
                alert.show();
                return;
            }

            String s1 = select1.getSelectionModel().getSelectedItem().toString();
            String s2 = select2.getSelectionModel().getSelectedItem().toString();

            if (s1.equals("Binary")) {
                if (s2.equals("Binary")) {
                    out = client.sendInput(selected + "," + "B"+","+"B" + "," + inputbox.getText());
                } else if (s2.equals("Octal")) {
                    out = client.sendInput(selected + "," + "B"+","+"O" + "," + inputbox.getText());
                } else if (s2.equals("Decimal")) {
                    out = client.sendInput(selected + "," + "B"+","+"D" + "," + inputbox.getText());
                } else if (s2.equals("Hexadecimal")) {
                    out = client.sendInput(selected + "," + "B"+","+"H" + "," + inputbox.getText());
                }
            } else if (s1.equals("Octal")) {
                if (s2.equals("Binary")) {
                    out = client.sendInput(selected + "," + "O"+","+"B" + "," + inputbox.getText());
                } else if (s2.equals("Octal")) {
                    out = client.sendInput(selected + "," + "O"+","+"O" + "," + inputbox.getText());
                } else if (s2.equals("Decimal")) {
                    out = client.sendInput(selected + "," + "O"+","+"D" + "," + inputbox.getText());
                } else if (s2.equals("Hexadecimal")) {
                    out = client.sendInput(selected + "," + "O"+","+"H" + "," + inputbox.getText());
                }
            } else if (s1.equals("Decimal")) {
                if (s2.equals("Binary")) {
                    out = client.sendInput(selected + "," + "D"+","+"B" + "," + inputbox.getText());
                } else if (s2.equals("Octal")) {
                    out = client.sendInput(selected + "," + "D"+","+"O" + "," + inputbox.getText());
                } else if (s2.equals("Decimal")) {
                    out = client.sendInput(selected + "," + "D"+","+"D" + "," + inputbox.getText());
                } else if (s2.equals("Hexadecimal")) {
                    out = client.sendInput(selected + "," + "D"+","+"H" + "," + inputbox.getText());
                }
            } else if (s1.equals("Hexadecimal")) {
                if (s2.equals("Binary")) {
                    out = client.sendInput(selected + "," + "H"+","+"B" + "," + inputbox.getText());
                } else if (s2.equals("Octal")) {
                    out = client.sendInput(selected + "," + "H"+","+"O" + "," + inputbox.getText());
                } else if (s2.equals("Decimal")) {
                    out = client.sendInput(selected + "," + "H"+","+"O" + "," + inputbox.getText());
                } else if (s2.equals("Hexadecimal")) {
                    out = client.sendInput(selected + "," + "H"+","+"H" + "," + inputbox.getText());
                }
            }

            inputbox2.setText(out);
        }

//        String out = client.sendInput(selected+","+input.getText());
//        output.setText(out);
    }

    @FXML
    private void ClearOnAction(){

        inputbox.clear();
        inputbox2.clear();
        outputbox.clear();

    }



}
