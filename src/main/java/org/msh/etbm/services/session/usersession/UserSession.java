package org.msh.etbm.services.session.usersession;

import org.msh.etbm.db.enums.NameComposition;
import org.msh.etbm.db.enums.UserView;
import org.msh.etbm.services.admin.admunits.data.AdminUnitData;

import java.util.List;
import java.util.UUID;

/**
 * User session information to be use during request
 * <p>
 * Created by rmemoria on 14/11/15.
 */
public class UserSession {
    private UUID userLoginId;

    // ID of the UserWorkspace
    private UUID userWorkspaceId;

    // information about the user
    private String userName;
    private UUID userId;
    private String userLoginName;

    // information about the workspace
    private UUID workspaceId;
    private String workspaceName;

    // information about the user unit
    private UUID unitId;
    private String unitName;

    // administrative unit of the user
    private AdminUnitData adminUnit;

    private UserView view;

    private boolean playOtherUnits;

    // true if user is administrator
    private boolean administrator;

    // list of permissions granted to the user
    private List<String> permissions;

    private String language;

    private NameComposition patientNameComposition;

    /**
     * Return true if the given permission is granted to the user
     *
     * @param perm the permission to check
     * @return true if permission is granted
     */
    public boolean isPermissionGranted(String perm) {
        return isAdministrator() ?
                true : permissions != null && permissions.contains(perm);
    }

    public UUID getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(UUID userLoginId) {
        this.userLoginId = userLoginId;
    }

    public UUID getUserWorkspaceId() {
        return userWorkspaceId;
    }

    public void setUserWorkspaceId(UUID userWorkspaceId) {
        this.userWorkspaceId = userWorkspaceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(UUID workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public UserView getView() {
        return view;
    }

    public void setView(UserView view) {
        this.view = view;
    }

    public boolean isPlayOtherUnits() {
        return playOtherUnits;
    }

    public void setPlayOtherUnits(boolean playOtherUnits) {
        this.playOtherUnits = playOtherUnits;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public AdminUnitData getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(AdminUnitData adminUnit) {
        this.adminUnit = adminUnit;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public NameComposition getPatientNameComposition() {
        return patientNameComposition;
    }

    public void setPatientNameComposition(NameComposition patientNameComposition) {
        this.patientNameComposition = patientNameComposition;
    }
}
