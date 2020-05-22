package com.mouzetech.ordersystem.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mouzetech.ordersystem.dto.ClienteNewDTO;
import com.mouzetech.ordersystem.resources.exceptions.FieldMessage;

public class TamanhoValidator implements ConstraintValidator<Tamanho, ClienteNewDTO> {
	@Override
	public void initialize(Tamanho ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// validando o cpf e cnpj
		
		if(objDto.getNome().length() < 5 || objDto.getNome().length() > 120) list.add(new FieldMessage("nome", "O tamanho deve ser entre 5 e 120 caracteres"));

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
