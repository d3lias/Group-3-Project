import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddTenantController {
    @FXML private ListView<Tenant> listView; // displays contact names
    @FXML private TextField tenantIDTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField removeTenantTextField;
    @FXML private TextField findByLastNameTextField;

    // interacts with the database
    private final TenantQueries tenantQueries = new TenantQueries();

    // stores list of Tenant objects that results from a database query
    private final ObservableList<Tenant> contactList =
            FXCollections.observableArrayList();

    // populate listView and set up listener for selection events
    public void initialize() {
        listView.setItems(contactList); // bind to contactsList
        getAllEntries(); // populates contactList, which updates listView

        // when ListView selection changes, display selected person's data
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    displayContact(newValue);
                }
        );
    }

    // get all the entries from the database to populate contactList
    private void getAllEntries() {
        contactList.setAll(tenantQueries.getAllTenants());
        selectFirstEntry();
    }

    // select first item in listView
    private void selectFirstEntry() {
        listView.getSelectionModel().selectFirst();
    }

    // display contact information
    private void displayContact(Tenant tenant) {
        if (tenant != null) {
            tenantIDTextField.setText(tenant.getTenantID());
            firstNameTextField.setText(tenant.getFirstName());
            lastNameTextField.setText(tenant.getLastName());
            phoneTextField.setText(tenant.getPhoneNumber());
            emailTextField.setText(tenant.getEmail());
        }
        else {
            tenantIDTextField.clear();
            firstNameTextField.clear();
            lastNameTextField.clear();
            emailTextField.clear();
            phoneTextField.clear();
        }
    }

    // add a new entry
    @FXML
    void addEntryButtonPressed(ActionEvent event) {
        int result = tenantQueries.addTenant(
                firstNameTextField.getText(), lastNameTextField.getText(),
                emailTextField.getText(),phoneTextField.getText());

        if (result == 1) {
            displayAlert(AlertType.INFORMATION, "Entry Added",
                    "New entry successfully added.");
        }
        else {
            displayAlert(AlertType.ERROR, "Entry Not Added",
                    "Unable to add entry.");
        }

        getAllEntries();
    }

    @FXML
    void removeEntryButtonPressed(ActionEvent event) {
        int result = tenantQueries.removeTenant(Integer.parseInt(removeTenantTextField.getText()));
        if (result == 1) {
            displayAlert(AlertType.INFORMATION, "Entry Removed",
                    "The entry successfully removed.");
        }
        else {
            displayAlert(AlertType.ERROR, "Entry Not Removed",
                    "Unable to remove entry.");
        }
        getAllEntries();
    }

    // find entries with the specified last name
    @FXML
    void findButtonPressed(ActionEvent event) {
        List<Tenant> tenant = tenantQueries.getTenantsByLastName(
                findByLastNameTextField.getText() + "%");

        if (tenant.size() > 0) { // display all entries
            contactList.setAll(tenant);
            selectFirstEntry();
        }
        else {
            displayAlert(AlertType.INFORMATION, "Lastname Not Found",
                    "There are no entries with the specified last name.");
        }
    }

    // browse all the entries
    @FXML
    void browseAllButtonPressed(ActionEvent event) {
        getAllEntries();
    }

    // display an Alert dialog
    private void displayAlert(
            AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}