package co.tmunited.bluebook.domain.vo;

/**
 * Created by Pp on 7/10/2017.
 */
public class ChartToLevelCareVO {

    private Long id;

    private String typeLevelCare;

    private String daysLoc;

    private Long chartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeLevelCare() {
        return typeLevelCare;
    }

    public void setTypeLevelCare(String typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
    }

    public String getDaysLoc() {
        return daysLoc;
    }

    public void setDaysLoc(String daysLoc) {
        this.daysLoc = daysLoc;
    }

    public ChartToLevelCareVO(Long id, String typeLevelCare, String daysLoc) {
        this.id = id;
        this.typeLevelCare = typeLevelCare;
        this.daysLoc = daysLoc;
    }

    public ChartToLevelCareVO(Long id, Long chartId, String typeLevelCare, String daysLoc) {
        this.id = id;
        this.chartId = chartId;
        this.typeLevelCare = typeLevelCare;
        this.daysLoc = daysLoc;
    }

    public ChartToLevelCareVO() {}

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
