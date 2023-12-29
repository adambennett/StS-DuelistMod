package duelistmod.persistence.data;

import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;

public class MetricsSettings extends DataCategory {

    private Boolean tierScoresEnabled = true;
    private Boolean webButtonsEnabled = true;
    private Boolean allowLocaleUpload = true;
    private Boolean logMetricsScoresToDevConsole = true;
    private Boolean tooltipsEnabled = true;

    public MetricsSettings() {
        this.category = "Metrics Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public MetricsSettings(MetricsSettings from) {
        this(from.getTierScoresEnabled(), from.getWebButtonsEnabled(), from.getAllowLocaleUpload(), from.getLogMetricsScoresToDevConsole(), from.getTooltipsEnabled());
    }

    public MetricsSettings(Boolean tierScoresEnabled, Boolean webButtonsEnabled, Boolean allowLocaleUpload, Boolean logMetricsScoresToDevConsole, Boolean tooltipsEnabled) {
        this();
        this.tierScoresEnabled = tierScoresEnabled;
        this.webButtonsEnabled = webButtonsEnabled;
        this.allowLocaleUpload = allowLocaleUpload;
        this.logMetricsScoresToDevConsole = logMetricsScoresToDevConsole;
        this.tooltipsEnabled = tooltipsEnabled;
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        return new LinkedHashSet<>();
    }

    public Boolean getTierScoresEnabled() {
        return tierScoresEnabled;
    }

    public void setTierScoresEnabled(Boolean tierScoresEnabled) {
        this.tierScoresEnabled = tierScoresEnabled;
    }

    public Boolean getWebButtonsEnabled() {
        return webButtonsEnabled;
    }

    public void setWebButtonsEnabled(Boolean webButtonsEnabled) {
        this.webButtonsEnabled = webButtonsEnabled;
    }

    public Boolean getAllowLocaleUpload() {
        return allowLocaleUpload;
    }

    public void setAllowLocaleUpload(Boolean allowLocaleUpload) {
        this.allowLocaleUpload = allowLocaleUpload;
    }

    public Boolean getLogMetricsScoresToDevConsole() {
        return logMetricsScoresToDevConsole;
    }

    public void setLogMetricsScoresToDevConsole(Boolean logMetricsScoresToDevConsole) {
        this.logMetricsScoresToDevConsole = logMetricsScoresToDevConsole;
    }

    public boolean getTooltipsEnabled() {
        return tooltipsEnabled != null ? tooltipsEnabled : true;
    }

    public void setTooltipsEnabled(Boolean tooltipsEnabled) {
        this.tooltipsEnabled = tooltipsEnabled;
    }
}
