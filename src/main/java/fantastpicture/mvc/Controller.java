package fantastpicture.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Objects;

import fantastpicture.mvc.entities.Image;
import fantastpicture.mvc.entities.Person;
import fantastpicture.mvc.service.Model;

public class Controller {
    private Model model;
    private boolean is_authenticated;

    @FXML
    private ListView<String> list_view;

    @FXML
    private ImageView image;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label note_label;

    @FXML
    private TextField note_field;

    @FXML
    private Button note_button;

    @FXML
    private Text error_text;


    private void __init__() {
        this.model = new Model();
        note_field.setDisable(true);
        note_button.setDisable(true);
    }

    @FXML
    public void initialize() {
        __init__();
        this.model.read_notes();
    }

    public void change_pic(MouseEvent event) {
        ArrayList<Image> images = this.model.get_images();
        for (Image img : images) {
            if (Objects.equals(img.getName(), list_view.getSelectionModel().getSelectedItem())) {
                set_image_view(image, img);
                float note = this.model.retrieve_note(login_field.getText(), list_view.getSelectionModel().getSelectedItem());
                note_field.setText(String.valueOf(note));
                break;
            }
        }
    }

    public void validate_login(ActionEvent event) {
        try {
            error_text.setText("");
            if (this.model.authenticate(login_field, password_field)) {
                populate_interface(list_view, image);
                float note = this.model.retrieve_note(login_field.getText(), list_view.getSelectionModel().getSelectedItem());
                note_field.setText(String.valueOf(note));
                note_field.setDisable(false);
                note_button.setDisable(false);
            }
        } catch (IllegalAccessError err) {
            list_view.getItems().clear();
            image.setImage(null);
            note_field.setDisable(true);
            note_button.setDisable(true);
            error_text.setText(err.getMessage());
        }
    }

    public void validate_note(ActionEvent event) {
        try {
            check_field(note_field, note_label.getText());

            Person person = this.model.retrieve_person_object_from_string(login_field.getText());
            Image image = this.model.retrieve_image_object_from_string(list_view.getSelectionModel().getSelectedItem());
            
            this.model.create_note(image, person, Float.parseFloat(note_field.getText()));
        } catch (IllegalArgumentException err) {
            System.err.println(err.getMessage());
        }
    }

    private void check_field(TextField input, String input_name) {
        if (input.getText().isEmpty()) {
            throw new IllegalArgumentException("Le champ " + input_name + " ne doit pas être vide");
        }
        if (!input.getText().matches("[0-9]*[.]?[0-9]?")) {
            throw new IllegalArgumentException("Le champ '" + input_name + "' ne doit contenir que des nombres positifs");
        }
        if (!(Float.parseFloat(input.getText()) >= 0 && Float.parseFloat(input.getText()) <= 20)) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20");
        }
    }

    public void populate_interface(ListView<String> list_view, ImageView image_view) {
        set_list_view(list_view);
        set_image_view(image_view, this.model.paris_pic);
    }

    private void set_image_view(ImageView image_view, Image image) throws IllegalArgumentException {
        try  {
            image_view.setImage(new javafx.scene.image.Image(image.getPath()));
        } catch (IllegalArgumentException err) {
            System.err.println(err.getMessage());
        }
    }

    private void set_list_view(ListView<String> list_view) {
        ArrayList<Image> images = this.model.get_images();
        if (list_view.getItems().isEmpty()) {
            for (Image image : images) {
                list_view.getItems().add(image.getName());
            }
            list_view.getSelectionModel().select(0);
        }
    }
}