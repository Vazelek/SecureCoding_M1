package hr.ticketmaster.finder.ai.ticketmasterfinderai.whitelist;

import hr.ticketmaster.finder.ai.ticketmasterfinderai.exception.IllegalClassException;
import hr.ticketmaster.finder.ai.ticketmasterfinderai.model.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WhitelistValidator {

    private static Set<Class> deserializationClassWhitelist;

    static {
        deserializationClassWhitelist = new HashSet<Class>();
        deserializationClassWhitelist.add(Ticket.class);
        deserializationClassWhitelist.add(List.class);
        deserializationClassWhitelist.add(ArrayList.class);
    }

    public static void validateSerializedFile(String binaryFile) throws IOException, IllegalClassException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFile))) {
            Object readObject;
            while ((readObject = ois.readObject()) != null){

                if (!deserializationClassWhitelist.contains(readObject.getClass())) {
                    throw new IllegalClassException("The class " + readObject.getClass() + " is not allowed");
                } else {
                    if (readObject instanceof List<?> ticketList) {

                        for (Object object : ticketList) {
                            if (!deserializationClassWhitelist.contains(object.getClass())) {
                                throw new IllegalClassException("The class " + object.getClass() + " is not allowed");
                            }
                        }
                        ticketList.forEach(System.out::println);
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new IllegalClassException("There was a problem with deserialization!", ex);
        }
    }

}
