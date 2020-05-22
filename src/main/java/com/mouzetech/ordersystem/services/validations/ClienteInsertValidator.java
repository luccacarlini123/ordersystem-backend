package com.mouzetech.ordersystem.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mouzetech.ordersystem.domain.enums.TipoCliente;
import com.mouzetech.ordersystem.dto.ClienteNewDTO;
import com.mouzetech.ordersystem.resources.exceptions.FieldMessage;
import com.mouzetech.ordersystem.services.validations.util.cpfCnpjValidatorUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// validando o cpf e cnpj
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !cpfCnpjValidatorUtil.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !cpfCnpjValidatorUtil.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
