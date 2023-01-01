package store;

import dev.morphia.Datastore;
import entity.TraxorUser;

public class UserStore {
    Datastore datastore;

    public UserStore(Datastore datastore) {
        this.datastore = datastore;
    }

    public void saveUser(TraxorUser user) {
        datastore.save(user);
    }

    public void deleteUser(TraxorUser user) {
        datastore.delete(user);
    }
}
