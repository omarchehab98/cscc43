package cscc43.assignment.model;

import java.util.List;

import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Role")
public class Role {
    @Column(name="ID")
    private Integer id;
    @Column(name="Description")
    private String description;
    @OneToMany(targetEntity=CrewMember.class, mappedBy="role")
    private List<CrewMember> crewMembers;

    public Role(Integer id, String description, List<CrewMember> crewMembers) {
        this.id = id;
        this.description = description;
        this.crewMembers = crewMembers;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CrewMember> getCrewMembers() {
        return this.crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
