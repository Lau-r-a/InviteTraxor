package store;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import entity.TraxorInvite;

import java.util.List;

public class InviteStore {

    private final Datastore datastore;

    public InviteStore (Datastore datastore) {
        this.datastore = datastore;
    }

    public List<TraxorInvite> getAllInvites() {
        Query<TraxorInvite> query = datastore.find(TraxorInvite.class);
        return query.stream().toList();
    }

    public void saveInvite(TraxorInvite traxorInvite) {
        datastore.save(traxorInvite);
    }

    public void deleteInvite(TraxorInvite traxorInvite) {
        datastore.delete(traxorInvite);
    }

    public void deleteInvite(String code) {
        Query<TraxorInvite> query = datastore.find(TraxorInvite.class);
        query.filter(Filters.eq("code", code));
        datastore.delete(query);
    }
}
