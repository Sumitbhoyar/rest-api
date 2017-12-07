package com.hr.recruitment.cucumber;

import com.hr.recruitment.ExerciseApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ExerciseApplication.class)
public class FeatureTestConfiguration {


}
