package br.com.ff.arch_beaver.common.config;

import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserSessionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {

    private static final ThreadLocal<UserEntity> currentUser = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<UserSessionEntity> currentSession = ThreadLocal.withInitial(() -> null);

    public static void setCurrentUser(UserEntity user) {
        currentUser.set(user);
    }
    public static UserEntity getCurrentUser() {
        return currentUser.get();
    }
    public static void clearUser() {
        currentUser.remove();
    }

    public static void setCurrentSession(UserSessionEntity session) {
        currentSession.set(session);
    }
    public static UserSessionEntity getCurrentSession() {
        return currentSession.get();
    }

    public static void clearAll() {
        currentUser.remove();
        currentSession.remove();
    }

}
