//package com.dfchallenge.twitterclone.middleware;
//
//import com.dfchallenge.twitterclone.security_helpers.JWTServices;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//@WebMvcTest
//@ExtendWith(SpringExtension.class)
//public class JWTAuthenticationFilterTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @MockBean
//    private JWTServices jwtServices;
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = webAppContextSetup(context)
//                .addFilters(new JWTAuthenticationFilter(jwtServices, userDetailsService))
//                .build();
//    }
//
//    @Test
//    public void whenValidToken_thenAuthenticate() throws Exception {
//        String validToken = "valid.token.here";
//        Integer accountId = 1;
//        UserDetails userDetails = mock(UserDetails.class);
//        when(jwtServices.extractAccountId(validToken)).thenReturn(accountId);
//        when(jwtServices.isTokenValid(validToken, accountId)).thenReturn(true);
//        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
//
//        mockMvc.perform(get("/some-protected-url")
//                        .cookie(new Cookie("token", validToken)))
//                .andExpect(status().isOk());
//
//        verify(userDetailsService).loadUserByUsername(Integer.toString(accountId));
//        verify(jwtServices).isTokenValid(validToken, accountId);
//    }
//
//    @Test
//    public void whenInvalidToken_thenDoNotAuthenticate() throws Exception {
//        String invalidToken = "invalid.token.here";
//
//        when(jwtServices.extractAccountId(invalidToken)).thenReturn(null);
//
//        mockMvc.perform(get("/some-protected-url")
//                        .cookie(new Cookie("token", invalidToken)))
//                .andExpect(status().isOk());
//
//        verify(userDetailsService, never()).loadUserByUsername(any());
//        verify(jwtServices, never()).isTokenValid(anyString(), anyInt());
//    }
//
//    // Additional test cases to cover more scenarios
//}