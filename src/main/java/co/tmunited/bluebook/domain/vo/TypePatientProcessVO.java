package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.TypePage;
import co.tmunited.bluebook.domain.TypePatientProcess;

/**
 * Created by Tech Support on 8/10/2017.
 */
public class TypePatientProcessVO {

    private Long id;

    private String name;

    private String allow_ur;

    private String protect;

    private String status;

    private TypePage typePage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllow_ur() {
        return allow_ur;
    }

    public void setAllow_ur(String allow_ur) {
        this.allow_ur = allow_ur;
    }

    public String getProtect() {
        return protect;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TypePage getTypePage() {
        return typePage;
    }

    public void setTypePage(TypePage typePage) {
        this.typePage = typePage;
    }

    public TypePatientProcessVO() {
    }

    public TypePatientProcessVO(TypePatientProcess typePatientProcess) {
        this.id = typePatientProcess.getId();
        this.name = typePatientProcess.getName();
        this.allow_ur = typePatientProcess.getAllow_ur();
        this.protect = typePatientProcess.getProtect();
        this.status = typePatientProcess.getStatus();
        this.typePage = typePatientProcess.getTypePage();
    }
}
