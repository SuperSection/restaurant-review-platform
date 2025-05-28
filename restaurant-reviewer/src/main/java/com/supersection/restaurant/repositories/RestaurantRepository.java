package com.supersection.restaurant.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.supersection.restaurant.domain.entities.Restaurant;

@Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurant, String> {
  // TODO: Custom queries
}
