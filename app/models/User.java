package models;

import com.avaje.ebean.Ebean;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SecureSocialuser")
public class User extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String userId;

    @Constraints.Required
    public String providerId;

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    public String fullName;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String avatarUrl;

    @Constraints.Required
    public String hasher;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String salt;

    @Constraints.Required
    public String role;

    public static User findByUserId(String userId) {
        return Ebean.find(User.class).where().eq("userId", userId).findUnique();
    }

    public static User findByEmailAndProviderId(String email, String providerId) {
        return Ebean.find(User.class).where().eq("email", email).eq("providerId", providerId).findUnique();
    }
}
