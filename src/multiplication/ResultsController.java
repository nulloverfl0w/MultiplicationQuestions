package multiplication;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ResultsController {

    @FXML private TableView<SavedData> table;

    @FXML private TableColumn<SavedData, SavedData> date;
    @FXML private TableColumn<SavedData, SavedData> total;
    @FXML private TableColumn<SavedData, SavedData> wrong;
    @FXML private TableColumn<SavedData, SavedData> right;
    @FXML private TableColumn<SavedData, SavedData> duration;
    private static Stage primaryStage;

    private final String csvFile = "MultiplicationResults.csv";


    public static void showScene(Stage primaryStage) {
        primaryStage.setTitle("Multiplication Results");
        Parent root = null;
        try {
            root = FXMLLoader.load(ResultsController.class.getResource("results.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Scene scene = new Scene(root, 850, 500);
        scene.getStylesheets().add(QuestionController.class.getResource("app.css").toExternalForm());
        primaryStage.setScene(scene);
        ResultsController.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        if (new File(csvFile).exists()) {
            showTable();
        } else {
            mainMenu();
        }
    }

    // TODO: properly format table
    // TODO: add whiteboard background to this part also
    // TODO: remove quotations around items in table

    private final ObservableList<SavedData> dataList = FXCollections.observableArrayList();

    private void showTable() {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        wrong.setCellValueFactory(new PropertyValueFactory<>("wrong"));
        right.setCellValueFactory(new PropertyValueFactory<>("right"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.setItems(dataList);

        final String fieldDelimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            int whileCounter = 0;
            while (((line = br.readLine()) != null)) {
                if (whileCounter > 0) {
                    String[] fields = line.split(fieldDelimiter, -1);
                    SavedData savedData = new SavedData(fields[0], fields[1], fields[2], fields[3], fields[4]);
                    dataList.add(savedData);
                } else {
                    whileCounter = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setEditable(false);
    }

    public void mainMenu() {
        ChooserController.showScene(primaryStage);
    }

    @SuppressWarnings("unused") // getters are required for the table to work
    public class SavedData {
        private SimpleStringProperty date, total, wrong, right, duration;

        SavedData(String date, String total, String wrong, String right, String duration) {
            this.date = new SimpleStringProperty(date);
            this.total = new SimpleStringProperty(total);
            this.wrong = new SimpleStringProperty(wrong);
            this.right = new SimpleStringProperty(right);
            this.duration = new SimpleStringProperty(duration);
        }

        public String getRight() {
            return right.get();
        }


        public String getDate() {
            return date.get();
        }


        public String getTotal() {
            return total.get();
        }


        public String getWrong() {
            return wrong.get();
        }


        public String getDuration() {
            return duration.get();
        }

    }
}
