	package com.pplflw.recruitment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.pplflw.recruitment.entity.enums.EmployeeActionEvent;
import com.pplflw.recruitment.entity.enums.EmployeeState;


@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeActionEvent>{

	@Override
	public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeActionEvent> config)throws Exception {
		config.withConfiguration().autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeActionEvent> states) throws Exception {
	
		states.withStates().initial(EmployeeState.ADDED).state(EmployeeState.IN_CHECK).state(EmployeeState.APPROVED).end(EmployeeState.ACTIVE);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeActionEvent> transitions)
			throws Exception {

		transitions
		.withExternal()
		.source(EmployeeState.ADDED)
		.target(EmployeeState.IN_CHECK)
		.event(EmployeeActionEvent.IN_CHECK_ACTION)
		.and()
		.withExternal()
		.source(EmployeeState.IN_CHECK)
		.target(EmployeeState.ADDED)
		.event(EmployeeActionEvent.IN_CHECK_FAILURE_ACTION)
		.and()
		.withExternal()
		.source(EmployeeState.IN_CHECK)
		.target(EmployeeState.APPROVED)
		.event(EmployeeActionEvent.APPROVED_ACTION)
		.and()
		.withExternal()
		.source(EmployeeState.APPROVED)
		.target(EmployeeState.ACTIVE)
		.event(EmployeeActionEvent.ACTIVE_ACTION)
		;
		
	}
}
