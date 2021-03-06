package org.msh.etbm.services.session.changepassword;

import org.msh.etbm.commons.Messages;
import org.msh.etbm.commons.commands.CommandLog;
import org.msh.etbm.commons.commands.CommandTypes;
import org.msh.etbm.commons.entities.EntityValidationException;
import org.msh.etbm.db.entities.User;
import org.msh.etbm.services.security.UserUtils;
import org.msh.etbm.services.security.password.ChangePasswordResponse;
import org.msh.etbm.services.security.password.PasswordLogHandler;
import org.msh.etbm.services.security.password.PasswordUpdateService;
import org.msh.etbm.services.session.usersession.UserRequestService;
import org.msh.etbm.services.session.usersettings.UserSettingsFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Change users password
 * Created by msantos on 27/06/16.
 */
@Service
public class ChangePasswordService {

    @Autowired
    Messages messages;

    @Autowired
    UserRequestService userRequestService;

    @Autowired
    PasswordUpdateService passwordUpdateService;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Update the settings of the current user
     *
     * @param data instance of {@link UserSettingsFormData} containing the settings
     * @return The list of changed fields
     */
    @Transactional
    @CommandLog(handler = PasswordLogHandler.class, type = CommandTypes.SESSION_CHANGE_PWD)
    public ChangePasswordResponse changePassword(ChangePasswordFormData data) {
        User user = entityManager.find(User.class, userRequestService.getUserSession().getUserId());
        String hashPwd = UserUtils.hashPassword(data.getPassword());

        if (!user.getPassword().equals(hashPwd)) {
            throw new EntityValidationException(data, "password", null, "changepwd.wrongpass");
        }

        passwordUpdateService.updatePassword(user.getId(), data.getNewPassword());

        return new ChangePasswordResponse(user.getId(), user.getName(), "The user changed its own password.");
    }
}
