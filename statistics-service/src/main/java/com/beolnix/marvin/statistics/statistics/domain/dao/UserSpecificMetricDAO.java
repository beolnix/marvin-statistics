package com.beolnix.marvin.statistics.statistics.domain.dao;

import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSpecificMetricDAO extends PagingAndSortingRepository<UserSpecificMetric, String> {

}
