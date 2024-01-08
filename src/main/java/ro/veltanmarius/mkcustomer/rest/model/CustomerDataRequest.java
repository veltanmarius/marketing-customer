package ro.veltanmarius.mkcustomer.rest.model;

/**
 * @author Marius Veltan
 */
public interface CustomerDataRequest {
    String getEmail();

    String getStreet();

    String getNumber();

    String getZipCode();

    String getCity();

    String getCountry();
}