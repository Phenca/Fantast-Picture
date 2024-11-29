package fantastpicture.mvc.entities;

public class Note {
    public Person person;
    public Image image;
    public float note;

    public Note(Person person, Image image, float note) {
        this.person = person;
        this.image = image;
        this.note = note;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return image.getName() + ';' + person.getLogin() + ';' + note;
    }
}
