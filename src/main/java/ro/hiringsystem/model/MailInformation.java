package ro.hiringsystem.model;

import lombok.*;

import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailInformation {

    private UUID id;

    private String firstName;

    private String lastName;

    private String mail;

    private final Predicate<String> mailCondition = (mail) -> mail.contains("@yahoo") || mail.contains("@gmail");

    public MailInformation(String mail) {

        if (!mailCondition.test(mail)) {
            System.out.println("The e-mail address is not valid.");
        }

    }

}
