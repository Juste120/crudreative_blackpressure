package crudreative.backpressure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
@Table("USERS")
public record User(@Id Long id, String name, String email) {}
