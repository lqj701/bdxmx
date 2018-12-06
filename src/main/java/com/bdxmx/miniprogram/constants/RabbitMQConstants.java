package com.bdxmx.miniprogram.constants;

public interface RabbitMQConstants {

    /**
     * 消息通知路由
     */
    String UC_EXCHANGE_NOTICE = "uc_notice_topic";
    /**
     * 通知队列
     */
    String UC_NOTICE_QUEUE = "uc_notice_queue";

    /******************** 总消息 *************************/
    /**
     * 用户信息接收
     */
    String UC_NOTICE_USER_ROUTING_KEY = "uc_notice_" + EntityEnum.user.name();
    /**
     * 部门信息接收
     */
    String UC_NOTICE_DEPARTMENT_ROUTING_KEY = "uc_notice_" + EntityEnum.department.name();
    /**
     * 企业信息接收
     */
    String UC_NOTICE_ORGANIZATION_ROUTING_KEY = "uc_notice_" + EntityEnum.organization.name();
    /**
     * 账户信息接收
     */
    String UC_NOTICE_ACCOUNT_ROUTING_KEY = "uc_notice_" + EntityEnum.account.name();
    /**
     * bms信息接收
     */
    String UC_NOTICE_ORGANIZATIONDOCKINGBMS_ROUTING_KEY = "uc_notice_" + EntityEnum.organizationDockingBms.name();

    /********************* 进销存独立版消息 ************************/
    String jxc_postfix = "_jxc_ik";
    /**
     * 用户信息接收
     */
    String UC_NOTICE_USER_JXC_IK_ROUTING_KEY = UC_NOTICE_USER_ROUTING_KEY + jxc_postfix;
    /**
     * 部门信息接收
     */
    String UC_NOTICE_DEPARTMENT_JXC_IK_ROUTING_KEY = UC_NOTICE_DEPARTMENT_ROUTING_KEY + jxc_postfix;
    /**
     * 企业信息接收
     */
    String UC_NOTICE_ORGANIZATION_JXC_IK_ROUTING_KEY = UC_NOTICE_ORGANIZATION_ROUTING_KEY + jxc_postfix;
    /**
     * 账户信息接收
     */
    String UC_NOTICE_ACCOUNT_JXC_IK_ROUTING_KEY = UC_NOTICE_ACCOUNT_ROUTING_KEY + jxc_postfix;
    /**
     * bms信息接收
     */
    String UC_NOTICE_ORGANIZATIONDOCKINGBMS_JXC_IK_ROUTING_KEY = UC_NOTICE_ORGANIZATIONDOCKINGBMS_ROUTING_KEY
            + jxc_postfix;

    /********************* CRM独立版消息 ************************/
    String crm_postfix = "_crm_ik";
    /**
     * 用户信息接收
     */
    String UC_NOTICE_USER_CRM_IK_ROUTING_KEY = UC_NOTICE_USER_ROUTING_KEY + crm_postfix;
    /**
     * 部门信息接收
     */
    String UC_NOTICE_DEPARTMENT_CRM_IK_ROUTING_KEY = UC_NOTICE_DEPARTMENT_ROUTING_KEY + crm_postfix;
    /**
     * 企业信息接收
     */
    String UC_NOTICE_ORGANIZATION_CRM_IK_ROUTING_KEY = UC_NOTICE_ORGANIZATION_ROUTING_KEY + crm_postfix;
    /**
     * 账户信息接收
     */
    String UC_NOTICE_ACCOUNT_CRM_IK_ROUTING_KEY = UC_NOTICE_ACCOUNT_ROUTING_KEY + crm_postfix;
    /**
     * bms信息接收
     */
    String UC_NOTICE_ORGANIZATIONDOCKINGBMS_CRM_IK_ROUTING_KEY = UC_NOTICE_ORGANIZATIONDOCKINGBMS_ROUTING_KEY
            + crm_postfix;

    /********************* 进销存励销消息 ************************/
    String jxc_lx_postfix = "_jxc_lx";
    /**
     * 用户信息接收
     */
    String UC_NOTICE_USER_JXC_LX_ROUTING_KEY = UC_NOTICE_USER_ROUTING_KEY + jxc_lx_postfix;
    /**
     * 部门信息接收
     */
    String UC_NOTICE_DEPARTMENT_JXC_LX_ROUTING_KEY = UC_NOTICE_DEPARTMENT_ROUTING_KEY + jxc_lx_postfix;
    /**
     * 企业信息接收
     */
    String UC_NOTICE_ORGANIZATION_JXC_LX_ROUTING_KEY = UC_NOTICE_ORGANIZATION_ROUTING_KEY + jxc_lx_postfix;
    /**
     * 账户信息接收
     */
    String UC_NOTICE_ACCOUNT_JXC_LX_ROUTING_KEY = UC_NOTICE_ACCOUNT_ROUTING_KEY + jxc_lx_postfix;
    /**
     * bms信息接收
     */
    String UC_NOTICE_ORGANIZATIONDOCKINGBMS_JXC_LX_ROUTING_KEY = UC_NOTICE_ORGANIZATIONDOCKINGBMS_ROUTING_KEY
            + jxc_lx_postfix;

    enum MqEvent {
        login, logout, add, update, delete;

        public static MqEvent getMqEvent(String event) {
            for (MqEvent mqEvent : MqEvent.values()) {
                if (mqEvent.name().equals(event)) {
                    return mqEvent;
                }
            }
            return null;
        }
    }
}
