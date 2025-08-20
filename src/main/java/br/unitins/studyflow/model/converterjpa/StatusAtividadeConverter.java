package br.unitins.studyflow.model.converterjpa;

import br.unitins.studyflow.model.StatusAtividade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter (autoApply = true)
public class StatusAtividadeConverter implements AttributeConverter<StatusAtividade, Integer>{

    @Override
    public Integer convertToDatabaseColumn(StatusAtividade status) {
        return status == null ? null : status.getId();
    }

    @Override
    public StatusAtividade convertToEntityAttribute(Integer idStatus) {
        return StatusAtividade.valueOf(idStatus);
    }
    
}