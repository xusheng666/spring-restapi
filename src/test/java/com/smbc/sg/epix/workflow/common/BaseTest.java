package com.smbc.sg.epix.workflow.common;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.smbc.sg.epix.workflow.configuration.StartGWorkflowApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartGWorkflowApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseTest {

}
