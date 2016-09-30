package _02_spring_on_the_web._05_building_spring_web_applications._02_writing_a_simple_controller._03_passing_model_data_to_the_view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import _02_spring_on_the_web._05_building_spring_web_applications._02_writing_a_simple_controller._03_passing_model_data_to_the_view.data.SpittleRepository;
import _02_spring_on_the_web._05_building_spring_web_applications._02_writing_a_simple_controller._03_passing_model_data_to_the_view.entities.Spittle;

public class SpittleControllerTest {

    @Test
    public void spittleTest() throws Exception {

        SpittleController spittleController = new SpittleController();
        MockMvc mockMvc = standaloneSetup(spittleController).build();
        mockMvc.perform(get("/spittles")).andExpect(view().name("spittlesView"));

    }

    @Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(20);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                            .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                            .build();

        mockMvc
                .perform(get("/spittles"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList", hasItems(expectedSpittles.toArray())));

    }

    private List<Spittle> createSpittleList(int count) {
        List<Spittle> spittles = new ArrayList<Spittle>();
        for (int i = 0; i < count; i++) {
            spittles.add(new Spittle("Spittle " + i, new Date()));
        }
        return spittles;
    }

}
