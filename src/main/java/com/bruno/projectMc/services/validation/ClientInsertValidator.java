package com.bruno.projectMc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bruno.projectMc.domain.enums.ClientType;
import com.bruno.projectMc.dto.ClientNewDTO;
import com.bruno.projectMc.resources.exceptions.FieldMessage;
import com.bruno.projectMc.services.validation.utils.BR;

@ClientInsert
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getType().equals(ClientType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getType().equals(ClientType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("CpfOrCnpj", "CNPJ Inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMsg()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}