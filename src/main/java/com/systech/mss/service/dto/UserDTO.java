package com.systech.mss.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;

    @NotBlank
//    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

//    @Email(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
//    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private String fmIdentifier;

    @JsonProperty("profileId")
    private long profileId;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(),
                user.getEmail(), user.isActivated(),
                user.getLangKey(), user.getFmIdentifier(), user.getId());
    }

//    public UserDTO(Long id, @NotBlank @Pattern(regexp = LOGIN_REGEX) @
//            Size(min = 1, max = 50) String login, @Email(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE) @Size(min = 5, max = 100) String email,
//                   boolean activated, @Size(min = 2, max = 5) String langKey, String fmIdentifier, long profileId) {
    public UserDTO(Long id, @NotBlank  String login, @Size(min = 5, max = 100) String email,
                   boolean activated, @Size(min = 2, max = 5) String langKey, String fmIdentifier, long profileId) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.fmIdentifier = fmIdentifier;
        this.profileId = profileId;
    }

}
