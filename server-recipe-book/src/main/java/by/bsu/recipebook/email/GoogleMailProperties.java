package by.bsu.recipebook.email;

import by.bsu.recipebook.manager.GoogleMailManager;

import java.util.Properties;

class GoogleMailProperties {

    static Properties getGoogleMailProperties() {
        Properties properties = new Properties();
        properties.setProperty(GoogleMailManager.MAIL_SMTPS_HOST, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTPS_HOST));
        properties.setProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_CLASS, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_CLASS));
        properties.setProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_FALLBACK, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_FALLBACK));
        properties.setProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_PORT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_PORT));
        properties.setProperty(GoogleMailManager.MAIL_SMTP_PORT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_PORT));
        properties.setProperty(GoogleMailManager.MAIL_SMTPS_AUTH, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTPS_AUTH));
        properties.setProperty(GoogleMailManager.MAIL_TRANSPORT_PROTOCOL, GoogleMailManager.getProperty(GoogleMailManager.MAIL_TRANSPORT_PROTOCOL));
        properties.setProperty(GoogleMailManager.MAIL_SMTP_QUITWAIT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_QUITWAIT));
        return properties;
    }
}
