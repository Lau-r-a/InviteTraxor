package entity;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity
public class TraxorInvite {
    @Id
    private String code;
    private int uses;
    private long inviterId;

    public TraxorInvite() {
    }

    public TraxorInvite(String code, int uses, long inviterId) {
        this.code = code;
        this.uses = uses;
        this.inviterId = inviterId;
    }

    public String getCode() {
        return code;
    }

    public int getUses() {
        return uses;
    }

    public long getInviterId() {
        return inviterId;
    }

    public boolean equals(TraxorInvite traxorInvite) {
        return this.code.equals(traxorInvite.code) && this.uses == traxorInvite.uses && this.inviterId == traxorInvite.inviterId;
    }

    public boolean equalsUsage(TraxorInvite traxorInvite) {
        return traxorInvite != null && this.uses == traxorInvite.uses;
    }
}
