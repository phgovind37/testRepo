package com.conquer.test;

import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

import com.conquer.test.controller.HomeController;
import com.conquer.test.jpa.User;
import com.conquer.test.service.HomeService;


@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	private MockMvc mockMvc;
	 
    @Mock
    private HomeService homeServiceMock;
 
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(homeServiceMock))
                .build();
    }
    
    @Test
    public void test(){
    	assertEquals(2, 1+1);
    }
    
    @Test
    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
        User first = new User();
        first.setName("govind");
        first.setPassword("hanuma");

        User second = new User();
        second.setName("govind1");
        second.setPassword("hanuma1");

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
                                hasProperty("password", is("hanuma1"))
                        )
                )));

        verify(homeServiceMock, times(1)).getAllUsers();
        verifyNoMoreInteractions(homeServiceMock);
    }
    
}
