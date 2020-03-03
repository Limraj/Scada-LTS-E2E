package org.scadalts.e2e.page.core.criterias;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.RowIdentifier;
import org.scadalts.e2e.page.core.utils.XpathFactory;

@Data
@ToString
@EqualsAndHashCode
public class RowCriteria implements CriteriaObject {

    private final IdentifierObject identifier1;
    private final IdentifierObject identifier2;
    private final DictionaryObject type;
    private final Tag tag;
    private final CssClass cssClass;

    private RowCriteria(IdentifierObject identifier1, IdentifierObject identifier2, DictionaryObject type, Tag tag,
                       CssClass cssClass) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.type = type;
        this.tag = tag;
        this.cssClass = cssClass;
    }

    @Override
    public RowIdentifier getIdentifier() {
        return new RowIdentifier(identifier1.getValue() + " " + identifier2.getValue());
    }

    public static RowCriteria criteria(IdentifierObject identifier, DictionaryObject type,
                                            Tag tag) {
        return new RowCriteria(identifier, identifier, type, tag, CssClass.empty());
    }

    public static RowCriteria criteria(IdentifierObject identifier, DictionaryObject type,
                                       Tag tag, CssClass classCss) {
        return new RowCriteria(identifier, identifier, type, tag, classCss);
    }

    public static RowCriteria criteria(IdentifierObject identifier, Tag tag) {
        return new RowCriteria(identifier, identifier, EmptyType.ANY, tag, CssClass.empty());
    }

    public static RowCriteria criteria(IdentifierObject identifier1, IdentifierObject identifier2, Tag tag) {
        return new RowCriteria(identifier1, identifier2, EmptyType.ANY, tag, CssClass.empty());
    }

    public String getXpath() {
        if(!identifier2.equals(identifier1))
            return cssClass.equals(CssClass.empty()) ?
                    XpathFactory.xpath(tag, identifier1.getValue(), identifier2.getValue()) :
                    XpathFactory.xpath(tag, cssClass, identifier1.getValue(), identifier2.getValue());
        if (type == EmptyType.ANY)
            return cssClass.equals(CssClass.empty()) ?
                    XpathFactory.xpath(tag, identifier1.getValue()) :
                    XpathFactory.xpath(tag, cssClass, identifier1.getValue());
        return cssClass.equals(CssClass.empty()) ?
                XpathFactory.xpath(tag, identifier1.getValue(), type.getName()) :
                XpathFactory.xpath(tag, cssClass, identifier1.getValue(), type.getName());
    }
}
