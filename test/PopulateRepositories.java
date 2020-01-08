import controllers.repository.DrugRepositoryController;
import controllers.repository.UserRepositoryController;
import exceptions.DuplicateObjectException;
import exceptions.IdClashException;
import exceptions.OutOfRangeException;
import models.drugs.DrugStock;
import models.users.*;
import models.users.info.Address;
import models.users.info.Gender;
import models.users.info.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.fail;

public class PopulateRepositories {
    public static void main(String[] args) {
        populateUsers();
        populateDrugs();
    }

    private static void populateUsers(){
        UserRepositoryController controller = UserRepositoryController.getInstance();

        // Create a list of users.
        EnumMap< UserRole, ArrayList< User > > users = new EnumMap< >(UserRole.class);
        try{
            users.put(UserRole.ADMIN, new ArrayList<>(
                    Arrays.asList(
                            new Admin("4212", "Praeron", "Ortycos",
                                    new Address("The Old Bakery", "City Walls Rd", "AB42 5AN", "CLOCKHILL"),
                                    "admin")
                    )
            ));

            users.put(UserRole.SECRETARY, new ArrayList<>(
                    Arrays.asList(
                            new Secretary("2844", "Barex", "Matys",
                                    new Address("74 Walwyn Rd", "EX13 9AJ", "CHARDSTOCK"),
                                    "frag&crack"),

                            new Secretary("5739","Skatardova", "Beror",
                                    new Address("72 Colorado Way", "SL0 6NP", "RICHINGS PARK"),
                                    "lascannonftw")
                    )
            ));

            users.put(UserRole.DOCTOR, new ArrayList<>(
                    Arrays.asList(
                            new Doctor("4891", "Raldun", "Deathseeker",
                                    new Address("60 Ash Lane", "HR4 0NH", "YARSOP"),
                                    "let_me_in"),

                            new Doctor("5102", "Kvyrll", "Ironhanded",
                                    new Address("Pear End", "City Walls Rd", "AB42 5AN", "CLOCKHILL"),
                                    "chaos_sux"),

                            new Doctor("5024", "Nectohr", "Elgon",
                                    new Address("55 Circle Way", "LN7 9TZ", "CAISTOR"),
                                    "promethean12")
                    )
            ));

            users.put(UserRole.PATIENT, new ArrayList<>(
                    Arrays.asList(
                            new Patient("9012", "Castiel", "Fatus",
                                    new Address("Cherry House", "Cunnery Rd", "ME14 1LN", "MAIDSTONE"),
                                    "b0lter",
                                    LocalDate.of(1999, 9,23),
                                    Gender.MALE),

                            new Patient("1164", "Gremenes", "Mordatus",
                                    new Address("47 Petworth Rd", "GL7 5GF", "DUNTISBOURNE LEER"),
                                    "password",
                                    LocalDate.of(1983, 8, 27),
                                    Gender.MALE),

                            new Patient("3462", "Aegot", "Dragonmane",
                                    new Address("22 Graham Road", "TD11 6GP", "CHEEKLAW"),
                                    "3mp3er0r",
                                    LocalDate.of(1990, 1, 13),
                                    Gender.MALE),

                            new Patient("5352", "Sabrella", "Bles",
                                    new Address("129  Middlewich Road", "TA4 5JH", "FLAXPOOL"),
                                    "3xt3rm1n@tus",
                                    LocalDate.of(1999, 11, 12),
                                    Gender.FEMALE),

                            new Patient("1902", "Dissonya", "Inviel",
                                    new Address("21 Cloch Rd", "TR8 3TD", "ST MAWGAN"),
                                    "terminator",
                                    LocalDate.of(2001, 12, 15),
                                    Gender.FEMALE)
                    )
            ));

            // Populate the RepositoryController with the users.
            users.forEach((userRole, userArrayList) -> {
                try {
                    controller.add(userArrayList);

                }catch (DuplicateObjectException e){
                    fail("Added user already exists in the repository.");

                }
            });

            controller.save();

        }catch (OutOfRangeException e) {
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    private static void populateDrugs(){
        DrugRepositoryController controller = DrugRepositoryController.getInstance();

        ArrayList< DrugStock > drugStocks = new ArrayList<>(
                Arrays.asList(
                        new DrugStock("Paracetamol", "Painkiller", new ArrayList<>(), 100),
                        new DrugStock("Morphine", "Painkiller", new ArrayList<>(Arrays.asList("Hallucinations")), 25),
                        new DrugStock("Diazepam", "Vallium", new ArrayList<>(), 50),
                        new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(Arrays.asList("Stomach ulcers")), 500),
                        new DrugStock("Amoxicillin", "Antibiotic", new ArrayList<>(Arrays.asList("Nausea","Rash")), 200),
                        new DrugStock("Hydrocortisone Cream", "Steroid cream for Eczema", new ArrayList<>(Arrays.asList("Skin peeling")), 20),
                        new DrugStock("Anti-histamine", "Anti-Allergy", new ArrayList<>(Arrays.asList("Drowsiness")), 200),
                        new DrugStock("CBD oil", "Painkiller", new ArrayList<>(Arrays.asList("Fatigue")), 200),
                        new DrugStock("Aspirin", "Anti-Inflammatory and Painkiller", new ArrayList<>(Arrays.asList("Gastrointestinal bleeding", "Swelling")), 200),
                        new DrugStock("Exterminatus", "A last ditch effort.", new ArrayList<>(Arrays.asList("Death")), 5)
                )
        );

        controller.add(drugStocks);
        controller.save();
    }
}
