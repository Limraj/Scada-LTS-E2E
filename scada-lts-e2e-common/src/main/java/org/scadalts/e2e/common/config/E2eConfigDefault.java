package org.scadalts.e2e.common.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
class E2eConfigDefault implements E2eConfig {

    private int portApp;
    private String cronPattern;
    private boolean continuousMode;
    private String userSmtp;
    private String passwordSmtp;
    private String hostSmtp;
    private int portSmtp;
    private Set<SendTo> sendTo;
    private String sendFrom;
    private boolean debugEmailMode;
    private boolean notificationEmailMode;
    private long deleteEmailFromSentEmailsAfterMs;

    private BrowserRef browserRef;
    private int timeoutMs;
    private File driverFile;
    private int ctrlCode;
    private boolean headlessMode;
    private boolean driverManagerMode;
    private boolean screenshotMode;
    private boolean fastSetValueMode;
    private boolean proxyMode;
    private PageLoadStrategy pageLoadStrategy;
    private URL reportsUrl;
    private Path reportsFolder;
    private int pollingIntervalMs;
    private int portProxy;
    private String hostProxy;
    private String[] classesTestRefs;
    private String[] pointValuesToTests;
    private String[] browserOptionsArgs;
    private String[] browserOptionsPrefs;
    private int alarmListChangedAfterMs;
    private int alarmListNoChangedAfterMs;
    private int waitingAfterSetPointValueMs;
    private String graphicalViewName;
    private TestPlan testPlan;
    private String dataPointToChangeXid;
    private String dataPointToReadXid;

    private Level logLevel;
    private URL urlAppBeingTested;
    private String userName;
    private String password;
    private AuthType authType;

    private String titleEmail;
    private String dataSourceName;
    private String dataPointName;

    private String dataPointTargetXid;
    private String dataPointSourceXid;

}
