package org.guildcraft.minion325.utils.validation;

public class Verify {

    public static boolean isNotNull(Object... args){
        if (args == null)
            return false;
        for (Object object : args)
            if (object == null)
                return false;
        return true;
    }

}
