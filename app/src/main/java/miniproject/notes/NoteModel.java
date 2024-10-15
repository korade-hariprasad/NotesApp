package miniproject.notes;

public class NoteModel {
    private int id;
    private String time, title, body;

    public NoteModel(int id, String time, String title, String body) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.body = body;
    }

    public NoteModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
