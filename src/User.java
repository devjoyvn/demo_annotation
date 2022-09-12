@Entity
public class User {

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "ADDRESS")
    private String address;

    @Sanitizer
    private void Sanitizer() {
        this.name = this.name.trim();
        if (this.age < 0) {
            age = 18;
        }
    }

    public User(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}