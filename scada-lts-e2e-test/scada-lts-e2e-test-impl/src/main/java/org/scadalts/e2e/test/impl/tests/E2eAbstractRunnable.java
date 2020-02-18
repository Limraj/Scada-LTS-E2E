package org.scadalts.e2e.test.impl.tests;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;
import org.scadalts.e2e.webservice.core.config.WebServiceObjectConfigurator;

@Log4j2
public abstract class E2eAbstractRunnable implements E2eRunnable {

    @Setter
    private static NavigationPage navigationPage;

    public static void setup() {
        _setup();
        _login();
    }

    public static void close() {
        _close();
    }

    private static void _login() {
        logger.debug("login...");
        navigationPage = LoginPage.openPage()
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
        WebServiceObjectConfigurator.init(null, E2eConfiguration.sessionId);
    }

    private static void _setup() {
        TestCoreConfigurator.init();
        TestImplConfigurator.init();
        PageObjectConfigurator.init();
    }

    private static void _close() {
        if(navigationPage != null) {
            logger.debug("close...");
            navigationPage.closeWindows();
            navigationPage = null;
        }
    }

    public static boolean isLogged() {
        return navigationPage != null;
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }


}
