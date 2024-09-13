package reserves.internal.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * A Room type in the accomodation
 * 
 * RoomTypes describe the category each room fits, it's associated with 
 * internal.tariff package to calculate the total cost of a reserve
 */
@Entity
@Table(name = "T_ROOM_TYPE")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="ROOM_TYPE_NAME",unique = true)
    private String name;

    @Column(name="ROOM_TYPE_DESCRIPTION")
    private String description;

    private boolean shared; // shared rooms are not allowed to host children

    public RoomType(String name, boolean shared) {
        this.name = name;
        this.shared = shared;
    }

    public RoomType() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
