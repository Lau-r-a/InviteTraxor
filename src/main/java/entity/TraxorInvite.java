package entity;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.Date;

@Entity
public class TraxorInvite {
    @Id
    private String code;
    private int uses;
    private long inviterId;
    private long expiry;

    public TraxorInvite() {
        // Empty constructor for Morphia
    }

    public TraxorInvite(String code, int uses, long inviterId, long expiry) {
        this.code = code;
        this.uses = uses;
        this.inviterId = inviterId;
        this.expiry = expiry;
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

    public long getExpiry() {
        return expiry;
    }

    public boolean equals(TraxorInvite traxorInvite) {
        return this.code.equals(traxorInvite.code) && this.uses == traxorInvite.uses && this.inviterId == traxorInvite.inviterId;
    }

    public boolean equalsUsage(TraxorInvite traxorInvite) {
        return traxorInvite != null && this.uses == traxorInvite.uses;
    }
}
