package project.models.users.info;

import project.models.I_Printable;

import java.io.Serializable;

/**
 * A class that encapsulates an address.
 */
public class Address
        implements I_Printable, Serializable {
    private String _firstLine;
    private String _secondLine;
    private String _postcode;
    private String _county;

    /**
     * Creates an empty, dummy Address object. For testing purposes only.
     */
    public Address(){}

    /**
     * Creates a one line address object.
     *
     * @param firstLine the first line of the address.
     * @param postcode the address postcode.
     * @param county the address county.
     */
    public Address(String firstLine, String postcode, String county){
        _firstLine = firstLine;
        _secondLine = "";
        _postcode = postcode;
        _county = county;
    }

    /**
     * Creates a two line address object.
     *
     * @param firstLine the first line of the address.
     * @param secondLine the second line of the address.
     * @param postcode the address postcode.
     * @param county the address county.
     */
    public Address(String firstLine, String secondLine, String postcode, String county){
        _firstLine = firstLine;
        _secondLine = secondLine;
        _postcode = postcode;
        _county = county;
    }

    /**
     * @return the _firstLine variable.
     */
    public String getFirstLine() {
        return _firstLine;
    }

    /**
     * @return the _secondLine variable.
     */
    public String getSecondLine() {
        return _secondLine;
    }

    /**
     * @return the _postcode variable.
     */
    public String getPostcode() {
        return _postcode;
    }

    /**
     * @return the _county variable.
     */
    public String getCounty() {
        return _county;
    }

    /**
     * @param firstLine the new contents of the _firstLine variable.
     */
    public void setFirstLine(String firstLine) {
        _firstLine = firstLine;
    }

    /**
     * @param secondLine the new contents of the _secondLine variable.
     */
    public void setSecondLine(String secondLine) {
        _secondLine = secondLine;
    }

    /**
     * @param postcode the new contents of the _postcode variable.
     */
    public void setPostcode(String postcode) {
        _postcode = postcode;
    }

    /**
     * @param county the new contents of the _county variable.
     */
    public void setCounty(String county) {
        _county = county;
    }

    /**
     * @return the object as a string.
     */
    @Override
    public String toString(){

        return String.format("%s\n%s%s\n%s", _firstLine, _secondLine.equals("") ? _secondLine + "\n" : "", _postcode, _county);
    }

    /**
     * Validates the postcode against the UK postcode regular expression.
     * This can be found at: https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom
     * @param postcode the postcode to be validated.
     * @return TRUE if it is the correct format, FALSE otherwise.
     */
    public static boolean isPostcodeValid(String postcode){
        return postcode.matches("[A-Z]{1,2}[0-9][A-Z0-9]? ?[0-9][A-Z]{2}$");
    }
}
