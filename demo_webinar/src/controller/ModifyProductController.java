package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


import static controller.AddProductController.isProductValid;
import static controller.MainScreenController.productindex;
import static model.Inventory.*;
/** This creates modify product class*
 *  FUTURE ENHANCEMENT:
 * I would figure out a way to modify a product/part without creating a new part, make it easier on the end user
 */

public class ModifyProductController implements Initializable {
    private ObservableList<Part> partslist = FXCollections.observableArrayList();



    @FXML
    private TextField IDTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField InvTextField;

    @FXML
    private TextField PriceTextField;

    @FXML
    private TextField MaxTextField;

    @FXML
    private TextField MinTextField;

    @FXML
    private TextField SearchText;



    @FXML
    private Button AddProductButton;

    @FXML
    private Button RemovePartButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelProductButton;
    private Product product;
    @FXML
    private TableView<Part> allpartstableview;

    @FXML
    private TableColumn<Part, Integer> allpartsID;

    @FXML
    private TableColumn<Part, String> allpartsName;

    @FXML
    private TableColumn<Part, Integer> allpartsInv;

    @FXML
    private TableColumn<Part, Double> allpartsPrice;

    @FXML
    private TableView<Part> associatedpartstableview;

    @FXML
    private TableColumn<?, ?> associatedID;

    @FXML
    private TableColumn<?, ?> associatedName;

    @FXML
    private TableColumn<?, ?> associatedInv;

    @FXML
    private TableColumn<Product, Double> associatedPrice;
    private String errorMessage = new String();


    /** This populates the screen with the product being modified.
     * @param product the product to be shown
     */
    public void setProduct(Product product) {
        this.product = product;

        IDTextField.setText(String.valueOf(product.getProductID()));
        NameTextField.setText(product.getProductName());
        InvTextField.setText(String.valueOf(product.getProductInStock()));
        PriceTextField.setText(String.valueOf( product.getProductPrice()));
        MaxTextField.setText(String.valueOf(product.getMax()));
        MinTextField.setText(String.valueOf(product.getMin()));

    }



    /** This updates the table view with the associated part list.*/
    public void UpdateAssociatedPartTableView() {
        associatedpartstableview.setItems(partslist);
    }
    /** This updates the part table view with all-of-the parts.*/
    public void updatePartTableView() {
        allpartstableview.setItems(getAllParts());
    }
    /** tableviews.*/
    public void initialize(URL url, ResourceBundle rb) {
        partslist.clear();
        Product product = allProducts.get(productindex);
        allpartsID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        allpartsName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        allpartsPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));
        allpartsInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        allpartstableview.setItems(getAllParts());
        updatePartTableView();
        partslist = product.getAllAssociatedParts();

        associatedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        UpdateAssociatedPartTableView();


    }

    /** This adds the selected part to the associated parts list and table view. */
    public void AddPart(javafx.event.ActionEvent event) {
        Part part = allpartstableview.getSelectionModel().getSelectedItem();
        partslist.add(part);
        UpdateAssociatedPartTableView();
    }
    /** This removes a part from the associated parts tableview and list. */
    public void RemovePart(javafx.event.ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("are you sure you want to remove the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Part partdelete = associatedpartstableview.getSelectionModel().getSelectedItem();
            partslist.remove(partdelete);
        } else {
            System.out.println("You removed the part");
        }


    }
    /** This saves the product. Creates a new product to take the new one's place and deletes the old.   */
    public void SaveProduct(javafx.event.ActionEvent event)  {



        String ID = (IDTextField.getText());


        String name = NameTextField.getText();
        int stock = Integer.parseInt(InvTextField.getText());
        double price = Double.parseDouble(PriceTextField.getText());
        int max = Integer.parseInt(MaxTextField.getText());
        int min = Integer.parseInt(MinTextField.getText());

        try {
            errorMessage = isProductValid(name, Integer.parseInt(String.valueOf(min)), Integer.parseInt(String.valueOf(max)), Integer.parseInt(String.valueOf(stock)), Double.parseDouble(String.valueOf(price)), partslist, errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {


                System.out.println("Product name: " + name);
                Product newproduct = new Product(Integer.parseInt(String.valueOf(ID)), name, Double.parseDouble(String.valueOf(price)), Integer.parseInt(String.valueOf(stock)), Integer.parseInt(String.valueOf(min)), Integer.parseInt(String.valueOf(max)));
                newproduct.setProductID(ID);
                newproduct.setProductName(name);
                newproduct.setProductPrice(Double.parseDouble(String.valueOf(price)));
                newproduct.setProductInStock(Integer.parseInt(String.valueOf(stock)));
                newproduct.setMin(Integer.parseInt(String.valueOf(min)));
                for ( Part p: partslist){
                    newproduct.addAssociatedPart(p);}
                model.Inventory.updateProduct(newproduct);


                Parent addproduct = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene addProductsScene = new Scene(addproduct);
                Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addProductsStage.setScene(addProductsScene);
                addProductsStage.show();
            }

        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();

        }
    }

    /** This cancels modifying the product and returns you to the main screen. */
    public void CancelProduct(javafx.event.ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You canceled modifying the product");
        }}

    /** This searches for partial or full matches and updates the tableview with found parts. When searched with an empty field it resets. */
    public void searchbuttonpushed(javafx.event.ActionEvent event)
    {
        ObservableList<Part> searchedparts2 = FXCollections.observableArrayList();
        String stuff = SearchText.getText();

        for (Part p: allParts) {
            if (p.getName().contains(stuff)){
                searchedparts2.add(p);
            }
            try{
                int stuff2 = Integer.parseInt(SearchText.getText());
                if (p.getId()==stuff2) {
                    searchedparts2.add(p);
                }

            } catch (NumberFormatException e) {

            }
        }



        if (searchedparts2.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("part was not found");
            alert.showAndWait();

        }
        else{
            allpartstableview.setItems(searchedparts2);
        }
        if(stuff == " "){
            allpartstableview.setItems(allParts);

        }

    }}

