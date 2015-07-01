package com.conquer.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.conquer.test.jpa.User;
import com.conquer.test.service.HomeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@WebAppConfiguration
public class HomeControllerWebTest {
	
	MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private HomeService homeServiceMock;
	
	@Before
	public void setUp(){
		//We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
		Mockito.reset(homeServiceMock);
		 
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	/*@Test
	public void test(){
		assertEquals(10, 5*2);
	}*/
	
	@Test
    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
		 User first = new User();
	        first.setName("govind");
	        first.setPassword("hanuma");

	        User second = new User();
	        second.setName("govind1");
	        second.setPassword("hanuma7");

	        when(homeServiceMock.getAllUsers()).thenReturn(Arrays.asList(first, second));

	        mockMvc.perform(get("/"))
	                .andExpect(status().isOk())
	                //.andExpect(view().name(TodoController.VIEW_TODO_LIST))
	                .andExpect(forwardedUrl("home"))
	                .andExpect(model().attribute("users", hasSize(2)))
	                .andExpect(model().attribute("users", hasItem(
	                        allOf(
	                                hasProperty("name", is("govind")),
	                                hasProperty("password", is("hanuma"))
	                        )
	                )))
	                .andExpect(model().attribute("users", hasItem(
	                        allOf(
	                        		hasProperty("name", is("govind1")),
	                                hasProperty("password", is("hanuma7"))
	                        )
	                )));

	        verify(homeServiceMock, times(1)).getAllUsers();
	        verifyNoMoreInteractions(homeServiceMock);
    }
	
/*    @Test
    public void add_EmptyTodoEntry_ShouldRenderFormViewAndReturnValidationErrorForTitle() throws Exception {
        mockMvc.perform(get("/user/add"))
               // .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //.sessionAttr("session", new User())
                .andExpect(status().isOk())
               // .andExpect(view().name("addUser"))
                //.andExpect(forwardedUrl("addUser"))
               // .andExpect(model().attributeHasFieldErrors("user", "title"))
                .andExpect(model().attribute("user", hasProperty("name", nullValue())))
                .andExpect(model().attribute("user", hasProperty("password", isEmptyOrNullString())));

        verifyZeroInteractions(homeServiceMock);
    }*/

}
