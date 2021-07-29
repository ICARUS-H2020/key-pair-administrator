package aero.icarus2020.keypairadministrator.rest.entities;

import java.io.Serializable;

public class Role implements Serializable {

    private Long id;

    private String name;

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



    public enum RoleEnum {
        SUPERADMIN("Super Administrator");
//        ORGANISATIONADMIN("Organisation Administrator"),
//        ORGANISATIONUSER("Organisation User"),
//        ENDUSER("End User");

        private String friendlyName;

        RoleEnum(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getFriendlyName() {
            return friendlyName;
        }

        public String getValue(String locale) {

            if (null != locale && locale.contains("en")) {
                return friendlyName;
            } else {
                return "N/A";
            }
        }
        }
}//EoC
