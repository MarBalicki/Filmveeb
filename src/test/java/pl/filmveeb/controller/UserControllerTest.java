package pl.filmveeb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void when_accessingRegisterForm_then_newUserFormIsReturned() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/register"))
                .andExpect(content().string(containsString("firstName")))
                .andExpect(content().string(containsString("lastName")))
                .andExpect(content().string(containsString("birthDate")))
                .andExpect(content().string(containsString("phoneNumber")))
                .andExpect(content().string(containsString("city")))
                .andExpect(content().string(containsString("street")))
                .andExpect(content().string(containsString("country")))
                .andExpect(content().string(containsString("zipCode")))
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("password")))
                .andExpect(model().attribute("newUserDto", hasProperty("firstName", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("lastName", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("birthDate", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("phoneNumber", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("city", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("street", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("country", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("zipCode", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("email", nullValue())))
                .andExpect(model().attribute("newUserDto", hasProperty("password", nullValue())));
    }

    @Test
    public void given_validUser_when_sendingForm_thenRedirectToSuccess() throws Exception {
        this.mockMvc.perform(
                post("/register")
                        .param("firstName", "Tomek")
                        .param("lastName", "Poradzisz")
                        .param("birthDate", "1990-01-01")
                        .param("phoneNumber", "11111111")
                        .param("city", "Warszawa")
                        .param("street", "Wesoła")
                        .param("country", "PL")
                        .param("zipCode", "99-999")
                        .param("email", "tomek@tomek.pl")
                        .param("password", "tomek")
        ).andDo(print())
                .andExpect(redirectedUrl("/login"));
    }

}
