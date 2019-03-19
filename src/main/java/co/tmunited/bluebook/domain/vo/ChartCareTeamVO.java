package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.TypeSpeciality;

import java.io.Serializable;

/**
 * Created by Tech Support on 8/10/2017.
 */
public class ChartCareTeamVO implements Serializable {

    private Long id;

    private Long chartId;

    private EmployeeVO employee;

    private Long employeeId;

    private TypeSpeciality typeSpeciality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public EmployeeVO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeVO employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public TypeSpeciality getTypeSpeciality() {
        return typeSpeciality;
    }

    public void setTypeSpeciality(TypeSpeciality typeSpeciality) {
        this.typeSpeciality = typeSpeciality;
    }

    public ChartCareTeamVO() {
    }

    public ChartCareTeamVO(Long id, Long chartId, TypeSpeciality typeSpeciality, Employee employee) {
        this.id = id;
        this.chartId = chartId;
        this.typeSpeciality = typeSpeciality;
        this.employee = new EmployeeVO(employee);
    }
}
