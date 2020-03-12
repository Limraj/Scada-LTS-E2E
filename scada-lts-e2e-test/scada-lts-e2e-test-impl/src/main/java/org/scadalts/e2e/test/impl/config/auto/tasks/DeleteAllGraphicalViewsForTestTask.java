package org.scadalts.e2e.test.impl.config.auto.tasks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;

@Data
public class DeleteAllGraphicalViewsForTestTask implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        GraphicalViewObjectsCreator.deleteAllGraphicalViewsTest(navigationPage);
    }
}
