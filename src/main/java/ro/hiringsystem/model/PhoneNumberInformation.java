package ro.hiringsystem.model;

import lombok.*;

import java.util.UUID;
import java.util.function.Predicate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberInformation {

    private UUID id;

    private String phoneNumber;

    private String country;

    private final Predicate<String> phoneNumberCondition = (phoneNumber) -> phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+");

    public PhoneNumberInformation(String phoneNumber) {

        if (!phoneNumberCondition.test(phoneNumber)) {
            System.out.println("The phone number is not valid.");
        }

    }

}
