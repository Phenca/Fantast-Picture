package fantastpicture.mvc.service;

import javafx.scene.control.TextField;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import fantastpicture.mvc.entities.Image;
import fantastpicture.mvc.entities.Note;
import fantastpicture.mvc.entities.Person;


public class Model {
    public Image paris_pic = new Image("Paris", "C:\\Users\\Soran\\IdeaProjects\\Fantast-Picture\\src\\main\\resources\\fantastpicture\\mvc\\img\\paris.jpg");
    public Image melbourne_pic = new Image("Melbourne", "C:\\Users\\Soran\\IdeaProjects\\Fantast-Picture\\src\\main\\resources\\fantastpicture\\mvc\\img\\melbourne.jpg");
    public Image oak_bridge_pic = new Image("Pont en chêne", "C:\\Users\\Soran\\IdeaProjects\\Fantast-Picture\\src\\main\\resources\\fantastpicture\\mvc\\img\\pont_bois_chene.jpg");

    public Person soran_user = new Person("soran", "azerty123");
    public Person anonymous_user = new Person("guest", "1234");
    public Person admin_user = new Person("admin", "admin");

    public File notes_file = new File("src/main/resources/fantastpicture/mvc/notes");
    ArrayList<Note> notes_list = new ArrayList<>();

    public ArrayList<Image> get_images() {
        ArrayList<Image> images_list = new ArrayList<>();
        images_list.add(paris_pic);
        images_list.add(melbourne_pic);
        images_list.add(oak_bridge_pic);
        return images_list;
    }

    public ArrayList<Person> get_persons() {
        ArrayList<Person> persons_list = new ArrayList<>();
        persons_list.add(soran_user);
        persons_list.add(anonymous_user);
        persons_list.add(admin_user);
        return persons_list;
    }

    public boolean authenticate(TextField login, TextField password) throws IllegalAccessError {
        for (Person person : this.get_persons()) {
            if (Objects.equals(person.getLogin(), login.getText())) {
                if ((Objects.equals(person.getPassword(), password.getText()))) {
                    System.out.println("Authentication successful !");
                    return true;
                }
                break;
            }
        }
        throw new IllegalAccessError("Authentication unsuccessful !");
    }

    public float retrieve_note(String login_input, String list_view_selected_element) {
        Person person = retrieve_person_object_from_string(login_input);
        Image image = retrieve_image_object_from_string(list_view_selected_element);
        return retrieve_note_from_image_and_person(image, person);
    }

    public Person retrieve_person_object_from_string(String value) {
        for (Person p : get_persons()) {
            if (Objects.equals(p.getLogin(), value)) {
                return p;
            }
        }
        return null;
    }

    public Image retrieve_image_object_from_string(String value) {
        for (Image i : get_images()) {
            if (Objects.equals(i.getName(), value)) {
                return i;
            }
        }
        return null;
    }

    public float retrieve_note_from_image_and_person(Image image, Person person) {
        for (Note n :notes_list) {
            if ((n.getImage() == image) && (n.getPerson() == person)) {
                return n.getNote();
            }
        }
        return 0;
    }

    public void create_note(Image pic_name, Person person_name, Float note) {
        for (Note n :notes_list) {
            if ((n.getImage() == pic_name) && (n.getPerson() == person_name)) {
                notes_list.remove(n);
                break;
            }
        }
        notes_list.add(new Note(person_name, pic_name, note));
        write_note();
    }

    public void write_note() {
        try {
            if (!notes_file.exists()) {
                notes_file.createNewFile();
            }
            FileWriter writer = new FileWriter(notes_file, false);
            for (Note note : notes_list) {
                writer.write(note + "\n");
            }
            writer.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    }

    public void read_notes() {
        try {
            File file = new File("src/main/resources/fantastpicture/mvc/notes");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] splited_line = scanner.nextLine().split(";");
                    Person person = retrieve_person_object_from_string(splited_line[1]);
                    Image image = retrieve_image_object_from_string(splited_line[0]);
                    float note = Float.parseFloat(splited_line[2]);
                    notes_list.add(new Note(person, image, note));
                }
                scanner.close();
            }
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    }

}
