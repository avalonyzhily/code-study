package com.avalony.scheduled;


import com.avalony.license.LicenseStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ValidLicenseSchedule {
    private final LicenseStore licenseStore;
    @Autowired
    public ValidLicenseSchedule(LicenseStore licenseStore) {
        this.licenseStore = licenseStore;
    }

    @Scheduled(cron = "${valid.license.cron}")
    public void reportCurrentTime() {
        licenseStore.validatorExecute();
        System.out.println(licenseStore.getLicenseValid());
    }
}
