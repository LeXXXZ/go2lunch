package ru.lexxxz.go2lunch.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.util.UserUtil;
import ru.lexxxz.go2lunch.web.AbstractControllerTest;
import ru.lexxxz.go2lunch.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lexxxz.go2lunch.TestUtil.readFromJson;
import static ru.lexxxz.go2lunch.TestUtil.userHttpBasic;
import static ru.lexxxz.go2lunch.UserTestData.*;
import static ru.lexxxz.go2lunch.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER))
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                                                                     .contentType(MediaType.APPLICATION_JSON)
                                                                     .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        User created = UserUtil.createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(userService.getByEmail("newemail@ya.ru"), created);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonUtil.writeValue(updatedTo))
                                              .with(userHttpBasic(USER))
        )
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "email", null);

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonUtil.writeValue(updatedTo))
                                              .with(userHttpBasic(USER)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "admin@gmail.com", "newPassword");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonUtil.writeValue(updatedTo))
                                              .with(userHttpBasic(USER)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}