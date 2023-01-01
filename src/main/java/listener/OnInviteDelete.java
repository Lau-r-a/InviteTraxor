package listener;

import facade.TrackerFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.channel.server.invite.ServerChannelInviteDeleteEvent;
import org.javacord.api.listener.channel.server.invite.ServerChannelInviteDeleteListener;

public class OnInviteDelete implements ServerChannelInviteDeleteListener {
    private Logger logger = LogManager.getLogger(OnInviteDelete.class);

    private TrackerFacade trackerFacade;

    public OnInviteDelete(TrackerFacade trackerFacade) {
        this.trackerFacade = trackerFacade;
    }

    @Override
    public void onServerChannelInviteDelete(ServerChannelInviteDeleteEvent serverChannelInviteDeleteEvent) {
        logger.info("Invite deleted: {}",serverChannelInviteDeleteEvent.getCode());
        trackerFacade.delteInvite(serverChannelInviteDeleteEvent.getCode());
    }
}
