package by.bsu.recipebook.manager;

import java.util.ResourceBundle;

public class GoogleMailManager {
    public static final String MAIL_SMTPS_HOST = "mail.smtps.host";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTPS_AUTH = "mail.smtps.auth";
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_SMTP_QUITWAIT = "mail.smtp.quitwait";
    public static final String MAIL_COMPANY_EMAIL = "mail.company.email";
    public static final String MAIL_PASSWORD = "mail.password";
    private static final String RESOURCE_NAME = "googlemail";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private GoogleMailManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
