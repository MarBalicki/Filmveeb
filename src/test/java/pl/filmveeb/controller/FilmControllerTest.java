package pl.filmveeb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.security.Principal;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "spring", roles = "USER")
    @Test
    public void when_accessingForm_then_emptyFormIsReturned() throws Exception {
        this.mockMvc.perform(
                get("/addFilm")
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "test";
                            }
                        }))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addNewFilm"))
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("genre")))
                .andExpect(content().string(containsString("productionYear")))
                .andExpect(content().string(containsString("description")))
                .andExpect(content().string(containsString("posterUrl")))
                .andExpect(model().attribute("newFilm", hasProperty("title", nullValue())))
                .andExpect(model().attribute("newFilm", hasProperty("genre", nullValue())))
                .andExpect(model().attribute("newFilm", hasProperty("productionYear", nullValue())))
                .andExpect(model().attribute("newFilm", hasProperty("description", nullValue())))
                .andExpect(model().attribute("newFilm", hasProperty("posterUrl", nullValue())));
    }

    @WithMockUser(value = "spring", roles = "USER")
    @Test
    public void given_validFilm_when_sendingForm_then_redirectToFilms() throws Exception {
        this.mockMvc.perform(post("/addFilm")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return "test";
                    }
                })
                .param("title", "test")
                .param("genre", "KOMEDIA")
        ).andDo(print())
                .andExpect(redirectedUrl("/films"));
    }


}
