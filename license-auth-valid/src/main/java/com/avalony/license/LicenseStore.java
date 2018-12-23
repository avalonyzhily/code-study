package com.avalony.license;

public interface LicenseStore {

    boolean getLicenseValid();

    void validatorExecute();
}
