package HeadMaster;

import Database.DatabaseConnect;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DeptController {
    @FXML
    private JFXButton addDept;
    @FXML
    private JFXButton editDept;
    @FXML
    public TableView<Departments> deptTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn subColumn;
    @FXML
    private TableColumn headColumn;

    private Admin school = new Admin();

    ObservableList<Departments> data;
    @FXML
    private void showSchoolInfo(Event  e){
      school.showSchoolInfo(e);
    }
    @FXML
    private void showTeachers(Event e){
        school.showTeachers(e);
    }
    @FXML
    private void showStudents(Event e){
        school.showStudents(e);
    }
    @FXML
    private void showDept(Event e){
        school.showDepts(e);
        viewDepts();
    }
    @FXML
    private void logout(Event e){
        school.logout(e);
    }
    @FXML
    public void viewDepts(){
        idColumn.setCellValueFactory( new PropertyValueFactory<>("deptId"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("deptName"));

        subColumn.setCellValueFactory(new   PropertyValueFactory<>("subjNo"));

        headColumn.setCellValueFactory(new PropertyValueFactory<>("nameoHod"));

        deptTable.setItems(databaseRetrieve("SELECT * FROM DEPARTMENTS;"));

    }

    private ObservableList databaseRetrieve(String query) {
      this.data   = FXCollections.observableArrayList();

        try{
            ResultSet resultSet = DatabaseConnect.connectAndAccessDB(query);

            while (resultSet.next()){
                data.addAll(new Departments(resultSet.getInt("DEPTID"),
                        resultSet.getString("DEPTNAME"), resultSet.getInt("SUBNO"),
                        resultSet.getString("HEADNAME")));
               // System.out.println(resultSet.getString("DEPTNAME"));
            }
            DatabaseConnect.closeConnection(resultSet);
        }
        catch (SQLException exp){
            System.out.println();
        }
        return data;
    }
    @FXML
    public void initialize(){
        deptTable.setEditable(true);
        viewDepts();
    }


}
