package com.pplflw.recruitment.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import com.pplflw.recruitment.dao.EmployeeDAO;
import com.pplflw.recruitment.dto.EmployeeDTO;
import com.pplflw.recruitment.dto.UpdateEmployeeStateDTO;
import com.pplflw.recruitment.dto.mapper.EmployeeToDTOMapper;
import com.pplflw.recruitment.entity.Employee;
import com.pplflw.recruitment.entity.EmployeeStatesLog;
import com.pplflw.recruitment.entity.enums.EmployeeActionEvent;
import com.pplflw.recruitment.entity.enums.EmployeeState;
import com.pplflw.recruitment.service.EmployeeService;
import com.pplflw.recruitment.service.EmployeeStatesLogService;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired EmployeeDAO employeeDAO;
	@Autowired EmployeeStatesLogService employeeStatesLogService;
	@Autowired StateMachineFactory<EmployeeState, EmployeeActionEvent> stateMachineFactory ;
	
	@Override
	public void saveEmployee(EmployeeDTO employeeDTO) {
		Employee employee = EmployeeToDTOMapper.INSTANCE.dtoToEntity(employeeDTO);
		employee.setState(EmployeeState.ADDED);
		
		employeeDAO.save(employee);
	}

	@Override
	public List<EmployeeDTO> getAll() {
		return EmployeeToDTOMapper.INSTANCE.entitiesToDTOs(employeeDAO.findAll());
	}

	private Employee getEntityById (Integer id) {
		Optional<Employee> optional = employeeDAO.findById(id) ;
		if(optional.isPresent()) {
			return optional.get();
		}
		return null ;
	}
	
	@Override
	public EmployeeDTO getById(Integer id) {
		return EmployeeToDTOMapper.INSTANCE.entityToDTO(getEntityById(id));
	}

	@Override
	public void inCheckEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		
		StateMachine<EmployeeState, EmployeeActionEvent> state = build(updateEmployeeStateDTO.getEmployeeId());	
		Message<EmployeeActionEvent> event = MessageBuilder.withPayload(EmployeeActionEvent.IN_CHECK_ACTION)
				.setHeader("employeeID", updateEmployeeStateDTO.getEmployeeId())
				.build();
		state.sendEvent(event);
	}
	
	@Override
	public void approveEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		StateMachine<EmployeeState, EmployeeActionEvent> state = build(updateEmployeeStateDTO.getEmployeeId());	
		Message<EmployeeActionEvent> event = MessageBuilder.withPayload(EmployeeActionEvent.APPROVED_ACTION)
				.setHeader("employeeID", updateEmployeeStateDTO.getEmployeeId())
				.build();
		state.sendEvent(event);
	}
	
	@Override
	public void activateEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		StateMachine<EmployeeState, EmployeeActionEvent> state = build(updateEmployeeStateDTO.getEmployeeId());	
		Message<EmployeeActionEvent> event = MessageBuilder.withPayload(EmployeeActionEvent.ACTIVE_ACTION)
				.setHeader("employeeID", updateEmployeeStateDTO.getEmployeeId())
				.build();
		state.sendEvent(event);
	}

	/**
	 * this function is called from the state machine factory
	 */
	private void updateEmployeeStateById(Integer id, EmployeeState state) {
		Employee employee = getEntityById(id);
		employee.setState(state);
		employeeDAO.save(employee);

		EmployeeStatesLog employeeStatesLog = new EmployeeStatesLog();
		employeeStatesLog.setActionDate(new Date());
		employeeStatesLog.setEmployee(employee);
		employeeStatesLog.setState(state);
		
		employeeStatesLogService.save(employeeStatesLog);
	}

	private StateMachine<EmployeeState, EmployeeActionEvent> build(Integer empId) {
		Employee employee = getEntityById(empId);

		StateMachine<EmployeeState, EmployeeActionEvent> sm = this.stateMachineFactory.getStateMachine(String.valueOf(employee.getId()));
		sm.stop();
		sm.getStateMachineAccessor()
				.doWithAllRegions(sma -> {
					sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<EmployeeState, EmployeeActionEvent>() {
						@Override
						public void preStateChange(State<EmployeeState, EmployeeActionEvent> state, Message<EmployeeActionEvent> message, 
								Transition<EmployeeState, EmployeeActionEvent> transition, StateMachine<EmployeeState, EmployeeActionEvent> stateMachine , 
								StateMachine<EmployeeState, EmployeeActionEvent> rootStateMachine) {
							Optional.ofNullable(message).ifPresent(msg -> {
								Optional.ofNullable(Integer.class.cast(msg.getHeaders().getOrDefault("employeeID", -1)))
										.ifPresent(empID -> {
											EmployeeServiceImpl.this.updateEmployeeStateById(empID, state.getId());
										});
							});

						}
					});
					sma.resetStateMachine(new DefaultStateMachineContext<>(employee.getState(), null, null, null));
				});
		sm.start();
		return sm;
	}
}
	

