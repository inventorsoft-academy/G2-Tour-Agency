package co.inventorsoft.touragency.model;

//import javax.persistence.*;

//@Entity
//@Table(name = "agencies")
public class Agency {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false, unique = true)
    private int id;

//    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Agency() {
    }

    public Agency(String name) {
        this.name = name;
    }
}
