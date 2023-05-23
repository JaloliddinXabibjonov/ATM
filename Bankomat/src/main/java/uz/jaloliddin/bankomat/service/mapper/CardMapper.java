package uz.jaloliddin.bankomat.service.mapper;

import uz.jaloliddin.bankomat.domain.Card;
import uz.jaloliddin.bankomat.service.dto.CardDTO;

public interface CardMapper extends EntityMapper<CardDTO, Card>{
    @Override
    Card toEntity(CardDTO dto);

    @Override
    CardDTO toDto(Card entity);
}
