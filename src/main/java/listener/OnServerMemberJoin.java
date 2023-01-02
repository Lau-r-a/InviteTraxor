package listener;

import facade.TrackerFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class OnServerMemberJoin implements ServerMemberJoinListener {

    Logger logger = LogManager.getLogger(OnServerMemberJoin.class);
    private TrackerFacade trackerFacade;

    public OnServerMemberJoin(TrackerFacade trackerFacade) {
        this.trackerFacade = trackerFacade;
    }

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {
        final long id = serverMemberJoinEvent.getUser().getId();

        if (serverMemberJoinEvent.getUser().isBot()) {
            logger.info("Bot with id {} joined the server", id);
            return;
        }

        logger.info("User {} with id {} joined", serverMemberJoinEvent.getUser().getDiscriminatedName(), id);
        trackerFacade.linkJoinedUser(id);
    }
}
