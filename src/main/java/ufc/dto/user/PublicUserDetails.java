package ufc.dto.user;

public class PublicUserDetails {

    private Integer id;
    private String fullName;
//    private UserRole userRole;

    public PublicUserDetails() {
    }

    public PublicUserDetails(Integer id, String fullName/*, UserRole userRole*/) {
        this.id = id;
        this.fullName = fullName;
//        this.userRole = userRole;
    }


//    public UserRole getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(UserRole userRole) {
//        this.userRole = userRole;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
