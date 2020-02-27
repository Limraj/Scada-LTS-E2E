package org.scadalts.e2e.page.core.pages;

public interface PageObject<T extends PageObject<T>> extends Maximizable<T>, Waitable<T>,
        Refreshable<T>, PageLoadingTimeMeasurable<T>, PageContent<T>, GetCookie,
        GetUrl {


}
