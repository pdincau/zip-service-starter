package zip;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("place name")
    public String name;

    public String state;

    @SerializedName("state abbreviation")
    public String stateAbbreviation;

    public Place(String name, String state, String stateAbbreviation) {
        this.name = name;
        this.state = state;
        this.stateAbbreviation = stateAbbreviation;
    }
}
