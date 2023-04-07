package ro.hiringsystem.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.hiringsystem.model.MailInformation;
import ro.hiringsystem.model.PhoneNumberInformation;
import ro.hiringsystem.model.enums.UserType;

import java.beans.Transient;
import java.net.URL;
import java.util.AbstractList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User {

    private UUID id;

    private Optional<String> firstName;

    private Optional<String> lastName;

    private Optional<List<MailInformation>> mailList;

    private Optional<List<PhoneNumberInformation>> phoneNumberList;

    private transient Optional<AbstractList<UserType>> allRoles;

}
