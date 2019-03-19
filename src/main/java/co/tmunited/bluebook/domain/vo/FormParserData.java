package co.tmunited.bluebook.domain.vo;

/**
 * Created by PpTMUnited on 12/8/2016.
 */
public class FormParserData {

  public Long formId;

  public Long chartId;

  public Long facilityId;

  public Long primSec;

  public Integer patientAge;

  public Long getFormId() {
    return formId;
  }

  public void setFormId(Long formId) {
    this.formId = formId;
  }

  public Long getChartId() {
    return chartId;
  }

  public void setChartId(Long chartId) {
    this.chartId = chartId;
  }

  public Long getFacilityId() {
    return facilityId;
  }

  public void setFacilityId(Long facilityId) {
    this.facilityId = facilityId;
  }

  public Long getPrimSec() {
    return primSec;
  }

  public void setPrimSec(Long primSec) {
    this.primSec = primSec;
  }

  public Integer getPatientAge() {
    return patientAge;
  }

  public void setPatientAge(Integer patientAge) {
    this.patientAge = patientAge;
  }

  public FormParserData() {
  }

  public FormParserData(Long formId, Long chartId, Long facilityId, Long primSec, Integer patientAge) {
    this.formId = formId;
    this.chartId = chartId;
    this.facilityId = facilityId;
    this.primSec = primSec;
    this.patientAge = patientAge;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FormParserData)) return false;

    FormParserData that = (FormParserData) o;

    if (!formId.equals(that.formId)) return false;
    if (!chartId.equals(that.chartId)) return false;
    if (!facilityId.equals(that.facilityId)) return false;
    if (!primSec.equals(that.primSec)) return false;
    return patientAge.equals(that.patientAge);
  }

  @Override
  public int hashCode() {
    int result = formId.hashCode();
    result = 31 * result + chartId.hashCode();
    result = 31 * result + facilityId.hashCode();
    result = 31 * result + primSec.hashCode();
    result = 31 * result + patientAge.hashCode();
    return result;
  }
}
