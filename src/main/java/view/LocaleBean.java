/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.stereotype.Component;

/**
 *
 * @author vinicius
 */
@Component
public class LocaleBean {

    private static final String PT_BR = "pt";
    private static final String EN = "en";
    private static final String FR = "fr";
    private static final List<SelectItem> LOCALES;
    static {
        LOCALES = new ArrayList<SelectItem>(3);
        LOCALES.add(new SelectItem(FR, "Français"));
        LOCALES.add(new SelectItem(EN, "English"));
        LOCALES.add(new SelectItem(PT_BR, "Português"));
    }

    private String locale;

    /**
     * @return the locale
     */
    public String getLocale() {
        if (locale == null) {
            return FR;
        } else {
            return locale;
        }
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<SelectItem> getLocaleList() {
        return LOCALES;
    }

    public void changeLocale(ValueChangeEvent event) {
        locale = event.getNewValue().toString();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale(locale));
    }

}