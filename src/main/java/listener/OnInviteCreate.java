package listener;

import facade.TrackerFacade;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.javacord.api.event.channel.server.invite.ServerChannelInviteCreateEvent;
import org.javacord.api.listener.channel.server.invite.ServerChannelInviteCreateListener;


public class OnInviteCreate implements ServerChannelInviteCreateListener {

    public static Logger logger = LogManager.getLogger(OnInviteCreate.class);

    private TrackerFacade trackerFacade;

    public OnInviteCreate(TrackerFacade trackerFacade) {
        this.trackerFacade = trackerFacade;
    }

    @Override
    public void onServerChannelInviteCreate(ServerChannelInviteCreateEvent event) {
        logger.info("Invite created: {} by {}", event.getInvite().getCode(), event.getInvite().getInviter());
        trackerFacade.resyncInvites();
    }
}
