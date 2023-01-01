import api.InviteAPI;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import facade.TrackerFacade;
import io.github.cdimascio.dotenv.Dotenv;
import listener.OnInviteCreate;
import listener.OnInviteDelete;
import listener.OnServerMemberJoin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Traxor {
    private static final Logger logger = LogManager.getLogger(Traxor.class);

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        // Enable debug logging
        //FallbackLoggerConfiguration.setDebug(true);
        // Enable trace logging
        //FallbackLoggerConfiguration.setTrace(true);

        Datastore datastore = initMongo("localhost",
                dotenv.get("MONGO_INITDB_ROOT_USERNAME"),
                dotenv.get("MONGO_INITDB_ROOT_PASSWORD"),
                "traxor");

        DiscordApi api = new DiscordApiBuilder()
                .setAllIntents()
                .setToken(dotenv.get("DISCORD_TOKEN"))
                .login()
                .join();

        Server server = api.getServerById(Long.parseLong(dotenv.get("SERVER_ID"))).get();

        TrackerFacade trackerFacade = new TrackerFacade(datastore, new InviteAPI(server));
        trackerFacade.resyncInvites();

        api.addListener(new OnInviteCreate(trackerFacade));
        api.addListener(new OnInviteDelete(trackerFacade));
        api.addListener(new OnServerMemberJoin(trackerFacade));

        logger.info("Bot invite link: {}", api.createBotInvite());
    }

    private static Datastore initMongo(String hostname, String username, String password, String database) {
        String mongoUrl = "mongodb://" + username + ":" + password + "@" + hostname + ":27017";
        final Datastore datastore = Morphia.createDatastore(MongoClients.create(mongoUrl), database);
        datastore.getMapper().mapPackage("entity");
        datastore.ensureIndexes();
        return datastore;
    }
}
