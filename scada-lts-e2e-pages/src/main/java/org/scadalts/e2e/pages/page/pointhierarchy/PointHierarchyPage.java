package org.scadalts.e2e.pages.page.pointhierarchy;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class PointHierarchyPage extends MainPageObjectAbstract<PointHierarchyPage> {

    public PointHierarchyPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PointHierarchyPage getPage() {
        return this;
    }
}