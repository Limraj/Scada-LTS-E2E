package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointVarCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.scripts.EditScriptsPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class ScriptObjectsCreator implements CreatorObject<ScriptsPage, ScriptsPage> {

    private final NavigationPage navigationPage;
    private final ScriptCriteria[] scriptCriteria;
    private ScriptsPage scriptsPage;

    public ScriptObjectsCreator(NavigationPage navigationPage, ScriptCriteria... scriptCriteria) {
        this.navigationPage = navigationPage;
        this.scriptCriteria = scriptCriteria;
    }

    @Override
    public ScriptsPage deleteObjects() {
        ScriptsPage scriptsPage = openPage();
        for (ScriptCriteria criteria: scriptCriteria) {
            if(scriptsPage.containsObjectWithoutType(criteria)) {
                logger.info("delete object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                scriptsPage.openScriptEditor(criteria)
                        .deleteScript();
            }
        }
        return scriptsPage;
    }

    @Override
    public ScriptsPage createObjects() {
        ScriptsPage scriptsPage = openPage();
        for (ScriptCriteria criteria: scriptCriteria) {
            if(!scriptsPage.containsObjectWithoutType(criteria)) {
                logger.info("create object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                EditScriptsPage editScriptsPage = scriptsPage.openScriptCreator()
                        .setName(criteria.getIdentifier())
                        .waitOnPage(500)
                        .setXid(criteria.getXid())
                        .setDataPointCommands(criteria.isEnableDataPointCommands())
                        .setDataSourceCommands(criteria.isEnableDataSourceCommands())
                        .setScript(criteria.getScript());
                for (DataPointVarCriteria var : criteria.getDataPointVarCriterias()) {
                    editScriptsPage.addPointToContext(var.getDataPointCriteria())
                            .setVarName(var);
                }
                editScriptsPage.saveScript()
                        .containsObject(criteria);
            }
        }
        return scriptsPage.reopen();
    }

    @Override
    public ScriptsPage openPage() {
        if(scriptsPage == null) {
            scriptsPage = navigationPage.openScripts();
            return scriptsPage;
        }
        return scriptsPage.reopen();
    }
}
