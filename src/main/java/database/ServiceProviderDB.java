package database;

import entity.ServiceProvider;
import main.LoggerUtility ;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServiceProviderDB {
    private static Logger logger = LoggerUtility.getLogger();

    static List<ServiceProvider> serviceProviders = new ArrayList<>();
    static ServiceProvider serviceProvider = new ServiceProvider();

    private ServiceProviderDB() {
        throw new IllegalStateException("Utility class");
    }

    static {
        serviceProviders.add(new ServiceProvider("1", "mo@gmail", "0655433", "hebron", "mohamad", 1));
        serviceProviders.add(new ServiceProvider("000", "ta@gmail", "098866", "nablus", "tala", 2));
    }

    public static void addServiceProvider(ServiceProvider serviceProvider) {
        serviceProviders.add(serviceProvider);
    }

    public static List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public static void displayServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProvider == null) {
            logger.warning("This service provider is not exist\n");
        } else {

            String format = "|%-12s|%12s|%15s|%14s|%20s|%20s|\n";
            String temp=String.format(format, "ID", "Name", "Phone", "Address", "Email", "password");

            logger.info(temp);

temp= String.format(format,
        serviceProvider.getId(),
        serviceProvider.getName(),
        serviceProvider.getPhone(),
        serviceProvider.getAddress(),
        serviceProvider.getEmail(),
        serviceProvider.getPassword()
);
            logger.info(temp);
        }
    }

    public static void updateServiceProvider(ServiceProvider updatedServiceProvider) {
        for (int i = 0; i < serviceProviders.size(); i++) {
            ServiceProvider sp = serviceProviders.get(i);
            if (sp.getId() == updatedServiceProvider.getId()) {
                serviceProviders.set(i, updatedServiceProvider);
                logger.info("Service provider profile updated successfully.");
                return;
            }}}

    public static ServiceProvider getServiceProviderById(int id) {
        for (ServiceProvider serviceProvider : serviceProviders) {
            if (serviceProvider.getId() == id) {
                return serviceProvider;
            }
        }
        return null;
    }
}
