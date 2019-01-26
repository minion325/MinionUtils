package me.minion325.utils.other;

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
