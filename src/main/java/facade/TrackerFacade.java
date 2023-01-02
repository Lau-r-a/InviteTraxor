package facade;

import api.InviteAPI;
import dev.morphia.Datastore;
import entity.TraxorInvite;
import entity.TraxorUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import store.InviteStore;
import store.UserStore;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TrackerFacade {

    private final static Logger logger = LogManager.getLogger(TrackerFacade.class);

    private InviteStore inviteStore;
    private UserStore userStore;
    private InviteAPI inviteApi;

    public TrackerFacade(Datastore datastore, InviteAPI inviteApi) {
        inviteStore = new InviteStore(datastore);
        userStore = new UserStore(datastore);
        this.inviteApi = inviteApi;
    }

    public void delteInvite(String inviteCode) {
        inviteStore.deleteInvite(inviteCode);
    }

    public void linkJoinedUser(long joinedUser) {
        TraxorInvite invite = findInviteChanges();
        if (invite != null) {
            userStore.saveUser(new TraxorUser(joinedUser, invite.getInviterId()));
            logger.info("Linked user " + joinedUser + " to inviter " + invite.getInviterId());
        } else {
            logger.warn("No linkage was made for user with id {}.", joinedUser);
        }
    }

    private TraxorInvite findInviteChanges() {
        List<TraxorInvite> changedInvites = new LinkedList<>();
        try {
            //get remote invites
            Map<String, TraxorInvite> remoteInv = inviteApi.getInvites();
            //check for each local invite which one has changed
            inviteStore.getAllInvites().forEach(traxorInvite -> {
                if (traxorInvite.getExpiry() < System.currentTimeMillis() / 1000) {
                    //invite is no longer valid
                    inviteStore.deleteInvite(traxorInvite.getCode());
                } else if (!traxorInvite.equalsUsage(remoteInv.get(traxorInvite.getCode()))) {
                    logger.info("Invite {} has changed.", traxorInvite.getCode());
                    changedInvites.add(traxorInvite);
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Failed to get invites from discord", e);
        }

        if (changedInvites.isEmpty()) {
            logger.warn("No invites changed");
        } else if (changedInvites.size() > 1) {
            logger.warn("More than one invite changed! Impossible to determine which invite was used");
        } else {
            return changedInvites.get(0);
        }
        return null;
    }

    public void resyncInvites() {

        try {
            //get all invites from discord
            Map<String, TraxorInvite> remoteInvites = inviteApi.getInvites();
            //delete all invites
            inviteStore.getAllInvites().forEach(inviteStore::deleteInvite);
            //save newly fetched invites
            remoteInvites.forEach((code, invite) -> inviteStore.saveInvite(invite));
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Failed to get invites from discord", e);
        }

        logger.info("Invites resynced");
    }

}
