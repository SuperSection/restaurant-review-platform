package com.supersection.restaurant.domain.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeRange {

  @Field(type = FieldType.Keyword)
  private String openTime;

  @Field(type = FieldType.Keyword)
  private String closeTime;
}
