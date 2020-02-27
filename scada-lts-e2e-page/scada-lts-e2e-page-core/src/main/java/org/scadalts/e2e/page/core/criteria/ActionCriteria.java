package org.scadalts.e2e.page.core.criteria;

import lombok.Data;
import org.openqa.selenium.By;

@Data
public class ActionCriteria {

    private final ObjectCriteria objectCriteria;
    private final By selectAction;

}
