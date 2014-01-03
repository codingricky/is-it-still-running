package models;

import com.avaje.ebean.Ebean;
import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class PersistableToken extends Model {
    @Id
    public String uuid;
    public String email;
    public DateTime creationTime;
    public DateTime expirationTime;
    public boolean isSignUp;

    public static PersistableToken findByUuid(String uuid) {
        return new Finder<>(String.class, PersistableToken.class).byId(uuid);
    }

    public static PersistableToken findByEmail(String email) {
        return Ebean.find(PersistableToken.class).where().eq("email", email).findUnique();
    }

    public static List<PersistableToken> findAll() {
        return Ebean.find(PersistableToken.class).findList();
    }

    public static boolean deleteById(String uuid) {
        PersistableToken persistableToken = findByUuid(uuid);
        if (persistableToken != null) {
            persistableToken.delete();
            return true;
        } else {
            return false;
        }
    }

}
