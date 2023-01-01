package api;

import entity.TraxorInvite;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class InviteAPI {
    private Server server;

    public InviteAPI(Server server) {
        this.server = server;
    }

    public Map<String, TraxorInvite> getInvites() throws ExecutionException, InterruptedException {
        Set<RichInvite> discordInvites = server.getInvites().get();
        Map<String, TraxorInvite> currentInvites = new HashMap<>();

        for (RichInvite dcInv : discordInvites) {
            long user = dcInv.getInviter().isPresent() ? dcInv.getInviter().get().getId() : 0;
            TraxorInvite inv = new TraxorInvite(dcInv.getCode(), dcInv.getUses(), user);
            currentInvites.put(dcInv.getCode(), inv);
        }

        return currentInvites;
    }

}
