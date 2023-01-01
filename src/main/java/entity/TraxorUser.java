package entity;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity
public class TraxorUser {
    @Id
    private long id;
    private long invitedById;

    public TraxorUser() {
    }

    public TraxorUser(long id, long invitedById) {
        this.id = id;
        this.invitedById = invitedById;
    }
}
