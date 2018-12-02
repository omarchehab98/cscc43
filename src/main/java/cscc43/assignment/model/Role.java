package cscc43.assignment.model;

import java.util.List;
import java.util.ArrayList;

import cscc43.assignment.persistence.Id;
import cscc43.assignment.persistence.GeneratedValue;
import cscc43.assignment.persistence.Column;
import cscc43.assignment.persistence.Entity;
import cscc43.assignment.persistence.OneToMany;

@Entity(name="Role")
public class Role {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="Description")
    private String description;
    @OneToMany(targetEntity=CrewMember.class, mappedBy="role")
    private List<CrewMember> crewMembers;

    public Role() {
        this(-1l, "", new ArrayList<CrewMember>());
    }

    public Role(Long id) {
        this(id, "", new ArrayList<CrewMember>());
    }

    public Role(String description) {
        this(-1l, description, new ArrayList<CrewMember>());
    }

    public Role(Long id, String description, List<CrewMember> crewMembers) {
        this.id = id;
        this.description = description;
        this.crewMembers = crewMembers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = new Long(id);
    }

    public void setId(Long id) {
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

    public String toString() {
        return String.format(
            "Role(%d, %s)",
            id,
            description
        );
    }
}
