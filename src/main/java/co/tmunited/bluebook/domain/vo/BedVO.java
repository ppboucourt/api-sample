package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.AbstractAuditingEntity;
import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Facility;
import co.tmunited.bluebook.domain.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A BedVO.
 */
public class BedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String bed_name;

    private String bed_notes;

    private String status;

    private Long actualChart;

    private Room room;

    private Facility facility;

    private Chart activeChart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBed_name() {
        return bed_name;
    }

    public BedVO bed_name(String bed_name) {
        this.bed_name = bed_name;
        return this;
    }

    public void setBed_name(String bed_name) {
        this.bed_name = bed_name;
    }

    public String getBed_notes() {
        return bed_notes;
    }

    public BedVO bed_notes(String bed_notes) {
        this.bed_notes = bed_notes;
        return this;
    }

    public void setBed_notes(String bed_notes) {
        this.bed_notes = bed_notes;
    }

    public String getStatus() {
        return status;
    }

    public BedVO status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActual_chart() {
        return actualChart;
    }

    public BedVO actualChart(Long actualChart) {
        this.actualChart = actualChart;
        return this;
    }

    public void setActualChart(Long actualChart) {
        this.actualChart = actualChart;
    }

    public Room getRoom() {
        return room;
    }

    public BedVO room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Facility getFacility() {
        return facility;
    }

    public BedVO facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }




    public Chart getActiveChart() {
        return activeChart;
    }

    public void setActiveChart(Chart activeChart) {
        this.activeChart = activeChart;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BedVO bed = (BedVO) o;
        if(bed.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bed{" +
            "id=" + id +
            ", bed_name='" + bed_name + "'" +
            ", bed_notes='" + bed_notes + "'" +
            ", status='" + status + "'" +
            ", actualChart='" + actualChart + "'" +
            '}';
    }
}
