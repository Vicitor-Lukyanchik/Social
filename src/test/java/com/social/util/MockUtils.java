package com.social.util;

import com.social.entity.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.social.Constants.*;

public class MockUtils {

    public static Chat createChat() {
        return Chat.builder().name(CHAT_NAME).build();
    }

    public static Interest createInterest() {
        return Interest.builder().name(INTEREST_NAME).build();
    }

    public static User createUser() {
        return User.builder().username(USERNAME).password(PASSWORD)
                .status(STATUS).roles(Collections.emptyList())
                .build();
    }

    public static Profile createProfile(User user) {
        return Profile.builder()
                .firstname(FIRSTNAME).lastname(LASTNAME)
                .email(EMAIL).sex(SEX).age(AGE)
                .phone(PHONE).familyStatus(FAMILY_STATUS).town(TOWN)
                .user(user).createdGroups(Collections.emptyList())
                .joinGroups(Collections.emptyList())
                .chats(Collections.emptyList())
                .build();
    }

    public static Profile createProfile() {
        return Profile.builder()
                .firstname(FIRSTNAME).lastname(LASTNAME)
                .email(EMAIL)
                .sex(SEX).age(AGE)
                .phone(PHONE).familyStatus(FAMILY_STATUS).town(TOWN)
                .createdGroups(Collections.emptyList())
                .joinGroups(Collections.emptyList())
                .chats(Collections.emptyList())
                .build();
    }

    public static Group createGroup(Profile profile) {
        return Group.builder().name(GROUP_NAME)
                .profile(profile).build();
    }

    public static Group createGroup(Profile profile, Interest interest) {
        return Group.builder().name(GROUP_NAME)
                .profile(profile)
                .interest(interest).build();
    }

    public static Message createMessage(Profile profile, Chat chat) {
        return Message.builder().chat(chat).profile(profile)
                .text(MESSAGE_TEXT).dateTime(LocalDateTime.now()).build();
    }

    public static Post createPost(Group group, Chat chat) {
        return Post.builder().group(group).chat(chat).title(POST_TITLE)
                .text(POST_TEXT).dateTime(LocalDateTime.now()).build();
    }

    public static Role createRole() {
        return Role.builder().name(ROLE_NAME).status(STATUS).build();
    }
}
