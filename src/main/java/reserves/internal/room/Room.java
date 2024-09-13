package reserves.internal.room;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import reserves.internal.roomType.RoomType;

/*
 * A Room in the accomodation
 * 
 * A room can share it's avaliability
 */
@Entity
@Table(name = "T_ROOM")
public class Room implements Serializable {

    public enum RoomStatus {
        FREE, 
        OCCUPIED,
        UNCLEAN, 
        IN_MAINTENANCE
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name="ROOM_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="ROOM_TYPE_ID")
    private RoomType roomType;

    @Column(name="ROOM_STATUS")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private int occupation;

    @Column(name = "MAXIMUM_OCCUPATION")
    private int maxOccupation;

    @Column(name = "MAXIMUM_CHILDREN_OCCUPATION")
    private int maxChildrenOccupation;

    public Room(String name, RoomType roomType, int maxOccupation) {
        this.name = name;
        this.roomType = roomType;
        this.maxOccupation = maxOccupation;
        this.status = RoomStatus.FREE;
    }

    public Room() {
    }

    public boolean isAvaliable() {
        if (roomType.isShared()) {
            return occupation < maxOccupation;
        } else {
            return occupation == 0;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public int getMaxOccupation() {
        return maxOccupation;
    }

    public void setMaxOccupation(int maxOccupation) {
        this.maxOccupation = maxOccupation;
    }

    public int getMaxChildrenOccupation() {
        return maxChildrenOccupation;
    }

    public void setMaxChildrenOccupation(int maxChildrenOccupation) {
        this.maxChildrenOccupation = maxChildrenOccupation;
    }
}
