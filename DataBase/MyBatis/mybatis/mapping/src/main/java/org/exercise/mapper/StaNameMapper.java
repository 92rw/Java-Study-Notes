package org.exercise.mapper;

import org.exercise.entity.StaName;

public interface StaNameMapper {
    StaName getObjectByCode(Integer code);
    StaName getObjectByCode2(Integer code);
    StaName offerReverseObject(Integer code);
}
