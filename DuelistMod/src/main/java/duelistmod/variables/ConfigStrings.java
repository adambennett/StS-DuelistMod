package duelistmod.variables;

public class ConfigStrings
{
    public String API;
    public String SECRET_PAIR;
    
    public static ConfigStrings getMockPowerString() {
        final ConfigStrings retVal = new ConfigStrings();
        retVal.API = "";
        retVal.SECRET_PAIR = "";
        return retVal;
    }
}
