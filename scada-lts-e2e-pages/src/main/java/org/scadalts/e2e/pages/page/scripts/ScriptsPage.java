package org.scadalts.e2e.pages.page.scripts;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class ScriptsPage extends PageObjectAbstract<ScriptsPage> {

    public ScriptsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ScriptsPage getPage() {
        return this;
    }
}
