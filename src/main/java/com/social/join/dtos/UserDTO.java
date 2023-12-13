package com.social.join.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Email(message = "Email Error")
    private String email;

    @Size(min = 6)
    private String password;

    private List<PostDTO> likedPosts = new ArrayList<>();

    private String token;

    public void addLikedPost(PostDTO testPostToAdd) {
        Boolean updated = false;
        // If this userDTo has not yet the post dto in its list
        if (!this.likedPosts.contains(testPostToAdd)) {
            this.likedPosts.add(testPostToAdd);
            updated = true;
        }

        // If post dto has not yet this userDTO in its list
        assert testPostToAdd.getUsersWhoLikedThisPost() != null;
        if (!testPostToAdd.getUsersWhoLikedThisPost().contains(this)){
            testPostToAdd.getUsersWhoLikedThisPost().add(this);
        }

    }
}
