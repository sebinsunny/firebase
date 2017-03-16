package pack.sebinsunny.com.appster;

/**
 * Created by sebin on 16/03/17.
 */

public class App {
    private String title, desc, image;

    public App() {

    }

    public App(String title, String desc, String image) {
        this.image = title;
        this.desc = desc;
        this.image = image;


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
