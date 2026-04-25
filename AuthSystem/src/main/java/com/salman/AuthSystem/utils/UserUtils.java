package com.salman.AuthSystem.utils;

import java.util.UUID;

public class UserUtils {

    public static UUID parseUUID(String uuid) {
        return UUID.fromString(uuid); 
    }
    
}
