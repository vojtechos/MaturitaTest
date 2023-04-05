package com.example.maturitatest;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class HelloController {

    @FXML
    public Label thievs;
    Scanner scanner = new Scanner(System.in);
    private File chosenFile;
    private String from;
    private String to;
    private Double value;
    @FXML
    public BarChart barChart;
    @FXML
    public Button SelectFileButton;
    @FXML
    public Button AnaliseButton;

    public void onOpenFileClick(MouseEvent mouseEvent){

        Window window = ((Node) mouseEvent.getTarget()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        chosenFile = fileChooser.showOpenDialog(window);
        if (chosenFile != null) {
            System.out.println(chosenFile.getAbsolutePath());
            System.out.println();
        }
    }

    public void onAnaliseButtonClick() throws FileNotFoundException {

        if (chosenFile == null) {
            System.out.println("No file chosen.");
            return;
        }

        List<Transaction> list = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(chosenFile);
        scanner = new Scanner(fileInputStream);
        scanner.nextLine();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] lineArr = line.split(";");
            from = lineArr[1];
            to = lineArr[2];
            value = Double.parseDouble(lineArr[3]);
            // Výpis csv souboru
            //System.out.println(from + " ; " +  to + " ; " + value);
            //System.out.println();
            Transaction transaction = new Transaction(from, to, value);
            list.add(transaction);
        }

        System.out.println("Výpis souboru schován");
        System.out.println();

        ArrayList<String> names = new ArrayList<String>();
        names.add("Buhuš Volný");
        names.add("Adrian Babis");
        names.add("Milous Zeman");
        names.add("Pitomio Vokamura");
        names.add("Peter Straka");
        names.add("Donald Tramp");
        names.add("Mohamed Horvat");

        //TODO - Vypisuje některé dvojce jako nulové hodnoty idk proč
        for (int i = 0; i < names.size(); i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if (!names.get(i).equals(names.get(j))) {
                    System.out.println("Pair: " + names.get(i) + ", " + names.get(j));
                    checkTransaction(list, names.get(i), names.get(j));
                    System.out.println("Pair: " + names.get(j) + ", " + names.get(i));
                    checkTransaction(list, names.get(j), names.get(i));
                }
            }
        }
    }

    public void checkTransaction(List<Transaction> list, String from, String to){

        int amount = 0;
        Map<Character, Integer> characterIntegerMap = new HashMap<>();
        for (Transaction tr : list) {
            if(tr.getFrom().compareTo(from) == 0 && tr.getTo().compareTo(to) == 0){
                amount++;
                char firstDigit = tr.getValue().toString().charAt(0);
                Integer count = characterIntegerMap.get(firstDigit);
                if(count == null) {
                   count = 0;
               }
               count++;
               characterIntegerMap.put(firstDigit, count);
            }
        }
        System.out.println("Počet transakcí je: " + amount);

        for(Map.Entry<Character, Integer> entry : characterIntegerMap.entrySet()) {
            System.out.println(entry.getValue() / (double)amount);
        }
    }
}