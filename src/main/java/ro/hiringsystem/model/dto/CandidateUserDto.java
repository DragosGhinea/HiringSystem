package ro.hiringsystem.model.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.enums.UserType;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateUserDto extends UserDto {

    private CV cv;

    private File recommendation;

    private URL githubProfileLink;

    private URL linkedInProfileLink;
}
