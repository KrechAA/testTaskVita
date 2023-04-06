package com.krech.vita.domain.rest.request;

public class UpdateRoleRequest {

    private long userId;

    private int roleId;


    public UpdateRoleRequest(long userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
