package services;

import models.PersistableToken;
import models.User;
import play.Application;
import scala.Option;
import securesocial.core.*;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

public class DBUserService extends BaseUserService {

    public DBUserService(Application application) {
        super(application);
    }

    @Override
    public Identity doSave(Identity identity) {
        Identity existingIdentity = doFind(identity.identityId());
        if (existingIdentity != null) return existingIdentity;

        User user = new User();
        user.userId = identity.identityId().userId();
        user.providerId = identity.identityId().providerId();
        user.firstName = identity.firstName();
        user.lastName = identity.lastName();
        user.fullName = identity.fullName();
        user.email = identity.email().get();
        if (identity.avatarUrl().nonEmpty()) {
            user.avatarUrl = identity.avatarUrl().get();
        }
        user.hasher = identity.passwordInfo().get().hasher();
        user.password = identity.passwordInfo().get().password();
        if (identity.passwordInfo().get().salt().nonEmpty()) {
            user.salt = identity.passwordInfo().get().salt().get();
        }
        user.save();

        return identity;
    }

    @Override
    public void doSave(Token token) {
        PersistableToken persistableToken = new PersistableToken();
        persistableToken.uuid = token.uuid;
        persistableToken.email = token.email;
        persistableToken.creationTime = token.creationTime;
        persistableToken.expirationTime = token.expirationTime;
        persistableToken.isSignUp = token.isSignUp;
        persistableToken.save();
    }

    @Override
    public Identity doFind(IdentityId identityId) {
        User user = User.findByUserId(identityId.userId());
        if (user == null) return null;
        return createSocialUserFromUser(identityId, user);
    }

    private static SocialUser createSocialUserFromUser(IdentityId identityId, User user) {
        PasswordInfo passwordInfo = new PasswordInfo(user.hasher, user.password, Option.apply(user.salt));
        return new SocialUser(identityId,
                user.firstName,
                user.lastName,
                user.fullName,
                Option.apply(user.email),
                Option.apply(user.avatarUrl),
                AuthenticationMethod.UserPassword(),
                null,
                null,
                Option.apply(passwordInfo));
    }

    @Override
    public Token doFindToken(String uuid) {
        PersistableToken persistableToken = PersistableToken.findByUuid(uuid);
        if (persistableToken == null) return null;

        return createTokenfromPersistableToken(persistableToken);
    }

    private static Token createTokenfromPersistableToken(PersistableToken persistableToken) {
        Token token = new Token();
        token.uuid = persistableToken.uuid;
        token.email = persistableToken.email;
        token.creationTime = persistableToken.creationTime;
        token.expirationTime = persistableToken.expirationTime;
        token.isSignUp = persistableToken.isSignUp;
        return token;
    }

    @Override
    public Identity doFindByEmailAndProvider(String email, String providerId) {
        User registeredUser = User.findByEmailAndProviderId(email, providerId);
        if (registeredUser == null) return null;
        return createSocialUserFromUser(new IdentityId(registeredUser.userId, providerId), registeredUser);
    }

    @Override
    public void doDeleteToken(String uuid) {
        PersistableToken.deleteById(uuid);
    }

    @Override
    public void doDeleteExpiredTokens() {
        for (PersistableToken persistableToken : PersistableToken.findAll()) {
            Token token = createTokenfromPersistableToken(persistableToken);
            if (token.isExpired()) {
                persistableToken.delete();
            }
        }
    }
}
