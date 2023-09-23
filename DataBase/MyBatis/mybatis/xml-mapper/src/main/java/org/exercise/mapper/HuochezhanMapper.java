package org.exercise.mapper;

import org.exercise.entity.Huochezhan;

import java.util.List;

public interface HuochezhanMapper {
    void addStation(Huochezhan huochezhan);
    List<Huochezhan> listAllStations();
}
